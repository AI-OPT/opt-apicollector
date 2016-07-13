package com.ai.runner.apicollector.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.Tag;
import com.sun.javadoc.ThrowsTag;
import com.sun.javadoc.Type;

public class JavaDocletUtil {

    public static ThrowsTag find(ThrowsTag[] tags, Type type) {
        for (final ThrowsTag tag : tags) {
            if (type.equals(tag.exceptionType())) {
                return tag;
            }
        }
        return null;
    }

    public static ParamTag find(ParamTag[] tags, Parameter param) {
        for (final ParamTag tag : tags) {
            if (param.name().equals(tag.parameterName())) {
                return tag;
            }
        }
        return null;
    }

    /**
     * 获取方法的基本说明
     * 
     * @param doc
     * @return
     * @author zhangchao
     */
    public static String getMethodBriefComment(MethodDoc doc) {
        String commenText = doc.commentText();
        if (commenText == null) {
            return null;
        }
        String briefComment = commenText.contains(".") ? commenText.substring(0,
                commenText.indexOf(".")) : commenText;
        return briefComment;
    }

    /**
     * 获取方法的详细注解说明
     * 
     * @param doc
     * @return
     * @author zhangchao
     */
    public static String getMethodDetailComment(MethodDoc doc) {
        String commenText = doc.commentText();
        if (commenText == null) {
            return null;
        }
        String detailComment = commenText.contains(".") ? commenText.substring(commenText
                .indexOf(".") + 1) : commenText;
        return detailComment;
    }

    /**
     * 获取参数的注解说明
     * 
     * @param paramTags
     * @param parameter
     * @return
     * @author zhangchao
     * @ApiDocMethod
     */
    public static String getParameterComment(ParamTag[] paramTags, Parameter parameter) {
        ParamTag p = null;
        for (final ParamTag tag : paramTags) {
            if (parameter.name().equals(tag.parameterName())) {
                p = tag;
                break;
            }
        }
        return (p != null) ? p.parameterComment() : "";
    }

    /**
     * 获取方法指导注解标签的值
     * 
     * @param doc
     * @param tag
     * @return
     * @author zhangchao
     * @ApiDocMethod
     */
    public static String getMethodTagComment(MethodDoc doc, String tag) {
        Tag[] tags = doc.tags(tag);
        if (tags != null && tags.length > 0) {
            return tags[0].text() != null ? tags[0].text() : "";
        }
        return "";
    }

    /**
     * 获取API带版本信息的HASHCODE
     * 
     * @param interfaceName
     * @param method
     * @param version
     * @return
     * @author zhangchao
     * @ApiDocMethod
     */
    public static int getAPIHashCode(String interfaceName, String method, String version) {
        return (interfaceName + "." + method + "." + version).hashCode();
    }

    /**
     * 获取API不带版本信息的HASHCODE
     * 
     * @param interfaceName
     * @param method
     * @return
     * @author zhangchao
     * @ApiDocMethod
     */
    public static int getAPIHashCode(String interfaceName, String method) {
        return (interfaceName + "." + method).hashCode();
    }

    /**
     * 获取API参数类型HASHCODE
     * 
     * @param pType
     * @param version
     * @return
     * @author zhangchao
     * @ApiDocMethod
     */
    public static int getAPIParameterHashCode(String pType, String version) {
        return (pType + "." + version).hashCode();
    }

    /**
     * 获取API归属的HASHCODE
     * 
     * @param owner
     * @param ownerType
     * @return
     * @author zhangchao
     * @ApiDocMethod
     */
    public static int getAPIOwnerHashCode(String owner, String ownerType) {
        return (owner + "." + ownerType).hashCode();
    }

    public static int getAPIBaseHashCode(String pType, String belong) {
        return (pType + "." + belong).hashCode();
    }

    public static String getDateString() {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = time;
        return dfmt.format(date);
    }

    /**
     * 判断是否有对应名称的注解
     * 
     * @param ads
     * @param annotationName
     * @return
     * @author zhangchao
     * @ApiDocMethod
     */
    public static boolean hasAnnotationName(AnnotationDesc[] ads, String annotationName) {
        if (ads == null) {
            return false;
        }
        for (AnnotationDesc desc : ads) {
            String aname = desc.annotationType().name();
            if (aname.equals(annotationName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否支持REST协议
     * 
     * @param methodDoc
     * @return
     * @author zhangchao
     * @ApiDocMethod
     */
    public static boolean checkRestSupported(MethodDoc methodDoc) {
        boolean rest = JavaDocletUtil.hasAnnotationName(methodDoc.annotations(), "Path");
        return rest;
    }

    /**
     * 获取REST的地址
     * 
     * @param methodDoc
     * @return
     * @author zhangchao
     * @ApiDocMethod
     */
    public static String getRestRelativeURL(MethodDoc methodDoc) {
        boolean rest = checkRestSupported(methodDoc);
        if (!rest) {
            return null;
        }
        String restURL = JavaDocletUtil.getMethodTagComment(methodDoc, "RestRelativeURL");
        if (restURL == null || restURL.equals("")) {
            throw new RuntimeException("此服务[" + methodDoc.name()
                    + "]使用了Path注解，请指定@RestRelativeURL注释说明REST的地址后段");
        }
        return restURL.trim();
    }

    /**
     * 获取REST的method
     * 
     * @param methodDoc
     * @return
     * @author zhangchao
     * @ApiDocMethod
     */
    public static String getRestMethod(MethodDoc methodDoc) {
        boolean rest = checkRestSupported(methodDoc);
        if (!rest) {
            return null;
        }
        boolean post = JavaDocletUtil.hasAnnotationName(methodDoc.annotations(), "POST");
        if (post)
            return "POST";
        boolean get = JavaDocletUtil.hasAnnotationName(methodDoc.annotations(), "GET");
        if (get)
            return "GET";
        boolean put = JavaDocletUtil.hasAnnotationName(methodDoc.annotations(), "PUT");
        if (put)
            return "PUT";
        boolean delete = JavaDocletUtil.hasAnnotationName(methodDoc.annotations(), "DELETE");
        if (delete)
            return "DELETE";
        return null;

    }
}
