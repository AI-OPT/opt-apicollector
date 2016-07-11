package com.ai.runner.apicollector.doclet;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.flexible.core.util.StringUtils;

import com.ai.runner.apicollector.util.APIESFactory;
import com.ai.runner.apicollector.util.JavaDocletUtil;
import com.ai.runner.apicollector.util.ReflectUtil;
import com.ai.runner.apicollector.vo.APIClassDoc;
import com.ai.runner.apicollector.vo.APIClassFieldDoc;
import com.ai.runner.apicollector.vo.APIDoc;
import com.ai.runner.apicollector.vo.APIOwnerDoc;
import com.ai.runner.apicollector.vo.APIParamDoc;
import com.ai.runner.apicollector.vo.ESConfig;
import com.ai.runner.apicollector.vo.ElasticIndex;
import com.ai.runner.apicollector.vo.ElasticType;
import com.alibaba.fastjson.JSON;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.ParameterizedType;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Type;

public class APIDoclet extends Doclet {

    // 服务归属
    public static String belong;

    // 服务提供者
    private static String owner;

    // 服务类型
    private static String ownerType;

    // 服务版本
    private static String version;

    // 服务坐标分组标识
    private static String groupId;

    // 服务构件标识
    private static String artifactId;

    // ES配置
    private static ESConfig esconfig;

    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }

    public static int optionLength(String option) {
        return DocletOptions.getOptionLength(option);
    }

    public static boolean start(RootDoc root) {
        final DocletOptions option = new DocletOptions(root.options());
        owner = option.getOwner();
        ownerType = option.getOwnerType();
        version = option.getVersion();
        artifactId = option.getArtifactId();
        groupId = option.getGroupId();
        esconfig = JSON.toJavaObject(JSON.parseObject(option.getEsconfig()), ESConfig.class);
        belong = option.getBelong();
        excute(root);
        return true;
    }

    private static void excute(RootDoc rootDoc) {
        ClassDoc[] classes = rootDoc.classes();
        for (ClassDoc classDoc : classes) {
            buildAPIIndexs(classDoc);
        }
        // 构造API归属索引
        buildAPIOwnerIndex();

    }

    /**
     * 构造API归属索引
     * 
     * @author zhangchao
     * @ApiDocMethod
     */
    private static void buildAPIOwnerIndex() {
        APIOwnerDoc doc = new APIOwnerDoc();
        doc.setOwner(owner);
        doc.setOwnerType(ownerType);
        doc.setId(JavaDocletUtil.getAPIOwnerHashCode(owner, ownerType));
        APIESFactory.getElasticSearchHandler(esconfig).addIndex(
                ElasticIndex.API.name().toLowerCase(), ElasticType.API_OWNER.name().toLowerCase(),
                StringUtils.toString(doc.getId()), JSON.toJSONString(doc));

    }

    /**
     * 构造索引
     * 
     * @param classDoc
     * @author zhangchao
     */
    private static void buildAPIIndexs(ClassDoc classDoc) {
        APIClassDoc doc = new APIClassDoc();
        doc.setInterface(classDoc.isInterface());
        doc.setClassName(classDoc.qualifiedName());
        doc.setId(JavaDocletUtil.getAPIParameterHashCode(classDoc.qualifiedName(), version));
        doc.setCommentText(classDoc.commentText());
        doc.setVersion(version);
        doc.setCanUnfold(true);
        doc.setOwner(owner);
        doc.setOwnerType(ownerType);
        doc.setArtifactId(artifactId);

        if (classDoc.isInterface()) {
            // 如果是接口定义
            for (final MethodDoc methodDoc : classDoc.methods()) {
                /* 1.构造版本历史索引 */
                buildAPIVersionHistoryIndex(classDoc.qualifiedName(), methodDoc);
                /* 2.构造最新版本索引 */
                buildAPIVersionNewIndex(classDoc.qualifiedName(), methodDoc);
            }

        } else {
            // 否则是参数定义
            ClassDoc superClassDoc = classDoc.superclass();
            List<APIClassFieldDoc> classFields = new ArrayList<APIClassFieldDoc>();
            if (superClassDoc != null
                    && !Object.class.getName().equals(superClassDoc.qualifiedName())) {
                classFields.addAll(processClassFields(doc.getId(), superClassDoc));
            }
            List<APIClassFieldDoc> fields = processClassFields(doc.getId(), classDoc);
            classFields.addAll(fields);
            doc.setClassFields(classFields);
        }
        System.out.println(JSON.toJSONString(doc));
        // 构造API类详细信息索引信息
        buildAPIClassDetailIndex(doc);
    }

    /**
     * 构造API版本索引记录
     * 
     * @param interfaceName
     * @param methodDoc
     * @author zhangchao
     */
    private static void buildAPIVersionHistoryIndex(String interfaceName, MethodDoc methodDoc) {
        APIDoc doc = new APIDoc();
        doc.setArtifactId(artifactId);
        doc.setBriefComment(JavaDocletUtil.getMethodBriefComment(methodDoc));
        doc.setDetailComment(JavaDocletUtil.getMethodDetailComment(methodDoc));
        doc.setInterfaceName(interfaceName);
        doc.setGroupId(groupId);
        doc.setId(methodDoc.qualifiedName().hashCode());
        doc.setMethodName(methodDoc.name());
        doc.setOwner(owner);
        doc.setOwnerType(ownerType);
        doc.setVersion(version);
        doc.setApiCode(JavaDocletUtil.getMethodTagComment(methodDoc, "ApiCode"));
        doc.setId(JavaDocletUtil.getAPIHashCode(interfaceName, methodDoc.name(), version));
        doc.setPublishDate(JavaDocletUtil.getDateString());
        String author = JavaDocletUtil.getMethodTagComment(methodDoc, "author");
        doc.setAuthor(author);

        doc.setRestMethod(JavaDocletUtil.getRestMethod(methodDoc));
        doc.setRestRelativeURL(JavaDocletUtil.getRestRelativeURL(methodDoc));
        doc.setRestSupported(JavaDocletUtil.checkRestSupported(methodDoc));
        // 入参信息
        APIParamDoc[] apiParamDocs = processInAPIParamDoc(methodDoc);
        doc.setInAPIParamDocs(apiParamDocs);
        // 返回参数信息
        APIParamDoc returnAPIParamDoc = processReturnAPIParamDoc(methodDoc);
        doc.setReturnAPIParamDoc(returnAPIParamDoc);
        // 签名的异常信息
        List<String> exceptions = new ArrayList<String>();
        ClassDoc[] exceptDocs = methodDoc.thrownExceptions();
        if (exceptDocs != null) {
            for (ClassDoc exceptDoc : exceptDocs) {
                String exceptName = exceptDoc.typeName();
                exceptions.add(exceptName);
            }
        }
        doc.setExceptions(exceptions.toString().replaceAll("^\\[| |\\]$", ""));
        APIESFactory.getElasticSearchHandler(esconfig).addIndex(
                ElasticIndex.API.name().toLowerCase(),
                ElasticType.API_VERSION_HISTORY.name().toLowerCase(),
                StringUtils.toString(doc.getId()), JSON.toJSONString(doc));
    }

    /**
     * 构造API_VERSION_NEW 只含有最后发布版本号的索引
     * 
     * @param interfaceName
     * @param methodDoc
     * @author zhangchao
     */
    private static void buildAPIVersionNewIndex(String interfaceName, MethodDoc methodDoc) {
        APIDoc doc = new APIDoc();
        doc.setArtifactId(artifactId);
        doc.setBriefComment(JavaDocletUtil.getMethodBriefComment(methodDoc));
        doc.setDetailComment(JavaDocletUtil.getMethodDetailComment(methodDoc));
        doc.setInterfaceName(interfaceName);
        doc.setGroupId(groupId);
        doc.setId(methodDoc.qualifiedName().hashCode());
        doc.setMethodName(methodDoc.name());
        doc.setOwner(owner);
        doc.setOwnerType(ownerType);
        doc.setVersion(version);
        doc.setApiCode(JavaDocletUtil.getMethodTagComment(methodDoc, "ApiCode"));
        doc.setId(JavaDocletUtil.getAPIHashCode(interfaceName, methodDoc.name()));
        doc.setPublishDate(JavaDocletUtil.getDateString());
        String author = JavaDocletUtil.getMethodTagComment(methodDoc, "author");
        doc.setAuthor(author);
        doc.setRestMethod(JavaDocletUtil.getRestMethod(methodDoc));
        doc.setRestRelativeURL(JavaDocletUtil.getRestRelativeURL(methodDoc));
        doc.setRestSupported(JavaDocletUtil.checkRestSupported(methodDoc));
        // 入参信息
        APIParamDoc[] apiParamDocs = processInAPIParamDoc(methodDoc);
        doc.setInAPIParamDocs(apiParamDocs);
        // 返回参数信息
        APIParamDoc returnAPIParamDoc = processReturnAPIParamDoc(methodDoc);
        doc.setReturnAPIParamDoc(returnAPIParamDoc);
        // 签名的异常信息
        List<String> exceptions = new ArrayList<String>();
        ClassDoc[] exceptDocs = methodDoc.thrownExceptions();
        if (exceptDocs != null) {
            for (ClassDoc exceptDoc : exceptDocs) {
                String exceptName = exceptDoc.typeName();
                exceptions.add(exceptName);
            }
        }
        doc.setExceptions(exceptions.toString().replaceAll("^\\[| |\\]$", ""));
        APIESFactory.getElasticSearchHandler(esconfig).addIndex(
                ElasticIndex.API.name().toLowerCase(),
                ElasticType.API_VERSION_NEW.name().toLowerCase(),
                StringUtils.toString(doc.getId()), JSON.toJSONString(doc));
    }

    /**
     * 构造API参数类详细索引
     * 
     * @param doc
     * @author zhangchao
     * @ApiDocMethod
     */
    private static void buildAPIClassDetailIndex(APIClassDoc doc) {
        APIESFactory.getElasticSearchHandler(esconfig).addIndex(
                ElasticIndex.API.name().toLowerCase(),
                ElasticType.CLASS_DETAIL.name().toLowerCase(), StringUtils.toString(doc.getId()),
                JSON.toJSONString(doc));

    }

    /**
     * 处理类属性信息
     * 
     * @param classDoc
     * @return
     * @author zhangchao
     * @ApiDocMethod
     */
    private static List<APIClassFieldDoc> processClassFields(int pid, ClassDoc classDoc) {
        List<APIClassFieldDoc> classFields = new ArrayList<APIClassFieldDoc>();
        for (final FieldDoc fieldDoc : classDoc.fields()) {
            if (!fieldDoc.isStatic() && fieldDoc.isField()) {
                APIClassFieldDoc field = new APIClassFieldDoc();
                field.setParamName(fieldDoc.name());
                field.setCommentText(fieldDoc.commentText());
                field.setClassName(fieldDoc.type().qualifiedTypeName());
                boolean baseType = ReflectUtil.checkBaseType(fieldDoc.type().qualifiedTypeName());
                if (baseType) {
                    field.setId(fieldDoc.type().qualifiedTypeName().hashCode());
                    field.setCanUnfold(false);
                } else {
                    field.setId(JavaDocletUtil.getAPIParameterHashCode(fieldDoc.type()
                            .qualifiedTypeName(), version));
                    field.setCanUnfold(true);
                }
                field.setPid(pid);
                field.setVersion(version);
                classFields.add(field);
            }
        }
        return classFields;
    }

    /**
     * 处理服务的返回参数
     * 
     * @param doc
     * @return
     * @author zhangchao
     */
    private static APIParamDoc processReturnAPIParamDoc(MethodDoc doc) {
        APIParamDoc paramDocInfo = new APIParamDoc();
        // 泛型处理
        ParameterizedType parameterizedType = doc.returnType().asParameterizedType();
        if (parameterizedType != null) {
            Type[] types = parameterizedType.typeArguments();
            APIParamDoc[] genericAPIParamDocs = new APIParamDoc[types.length];
            int i = 0;
            for (Type type : types) {
                APIParamDoc genericAPIParamDoc = new APIParamDoc();
                genericAPIParamDoc.setClassName(type.qualifiedTypeName());
                genericAPIParamDoc.setName(type.simpleTypeName());
                genericAPIParamDoc.setVersion(version);
                boolean baseType = ReflectUtil.checkBaseType(type.qualifiedTypeName());
                if (baseType) {
                    genericAPIParamDoc.setId(type.qualifiedTypeName().hashCode());
                } else {
                    genericAPIParamDoc.setId(JavaDocletUtil.getAPIParameterHashCode(
                            type.qualifiedTypeName(), version));
                }
                genericAPIParamDoc.setCanUnfold(!baseType);
                genericAPIParamDocs[i] = genericAPIParamDoc;
                i++;
            }
            paramDocInfo.setGeneric(true);
            paramDocInfo.setGenericAPIParamDocs(genericAPIParamDocs);
        }
        paramDocInfo.setClassName(doc.returnType().qualifiedTypeName());
        String returnTag = JavaDocletUtil.getMethodTagComment(doc, "return");
        paramDocInfo.setId(paramDocInfo.getClassName().hashCode());
        paramDocInfo.setCommentText(returnTag);
        boolean baseType = ReflectUtil.checkBaseType(doc.returnType().qualifiedTypeName());
        if (baseType) {
            paramDocInfo.setId(doc.returnType().qualifiedTypeName().hashCode());
        } else {
            paramDocInfo.setId(JavaDocletUtil.getAPIParameterHashCode(doc.returnType()
                    .qualifiedTypeName(), version));
        }
        paramDocInfo.setCanUnfold(!baseType);
        paramDocInfo.setVersion(version);
        return paramDocInfo;
    }

    /**
     * 组织服务的入参列表
     * 
     * @param doc
     * @return
     * @author zhangchao
     */
    private static APIParamDoc[] processInAPIParamDoc(MethodDoc doc) {
        Parameter[] parameters = doc.parameters();
        ParamTag[] paramTags = doc.paramTags();
        if (parameters == null) {
            return null;
        }
        APIParamDoc[] apiParamDocs = new APIParamDoc[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter p = parameters[i];
            String commentText = JavaDocletUtil.getParameterComment(paramTags, p);
            boolean baseType = ReflectUtil.checkBaseType(p.type().qualifiedTypeName());
            APIParamDoc pDoc = new APIParamDoc();
            pDoc.setClassName(p.type().qualifiedTypeName());
            pDoc.setCommentText(commentText);
            pDoc.setVersion(version);
            if (baseType) {
                pDoc.setId(p.type().qualifiedTypeName().hashCode());
            } else {
                pDoc.setId(JavaDocletUtil.getAPIParameterHashCode(p.type().qualifiedTypeName(),
                        version));
            }
            pDoc.setName(p.name());
            pDoc.setCanUnfold(!baseType);
            apiParamDocs[i] = pDoc;
        }
        return apiParamDocs;
    }
}
