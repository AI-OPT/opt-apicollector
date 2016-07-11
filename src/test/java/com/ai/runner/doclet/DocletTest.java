package com.ai.runner.doclet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ai.runner.apicollector.doclet.APIDoclet;

public class DocletTest {

    public static void main(String[] args) throws Exception {
        String interfaceDir = "/Users/zhangchao/work/codespaces/runner-codes/Runner-Channel/channel-api/src/main/java/com/ai/runner/center/channel/api/chlchannelquery";
        String[] docargs = getInterfaceArgs(interfaceDir);
        com.sun.tools.javadoc.Main.execute(docargs);

    }

    public static String[] getInterfaceArgs(String interfaceDir) {
        File dataDir = new File(interfaceDir);
        // 存放目录及其子目录下的所有文件对象
        List<File> myfiles = new ArrayList<File>();
        // 开始遍历
        listDirectory(dataDir, myfiles);
        
        List<File> baseFiles = getBaseFiles();
        int size = myfiles == null ? 0 : myfiles.size();
        String[] docArgs = new String[19 + size+baseFiles.size()];
        docArgs[0] = "-doclet";
        docArgs[1] = APIDoclet.class.getName();
        docArgs[2] = "-private";
        docArgs[3] = "-owner";
        docArgs[4] = "runner-channel-center";
        docArgs[5] = "-ownerType";
        docArgs[6] = "runner";
        docArgs[7] = "-version";
        docArgs[8] = "1.0-SNAPSHOT";
        docArgs[9] = "-artifactId";
        docArgs[10] = "area";
        docArgs[11] = "-groupId";
        docArgs[12] = "com.ai.runner.center.channel";
        docArgs[13] = "-esconfig";
        docArgs[14] = "[{ip:localhost, port:9300}]";
        docArgs[15] = "-belong";
        docArgs[16] = "opt-runner";
        docArgs[17] = "-docletpath";
        docArgs[18] = "/Applications/software/devsoft/gradle-2.5/caches/modules-2/files-2.1/com.ai.runner/runner-base/1.0-SNAPSHOT/eb9f1bcf3dcdb523042fb21ce201ca32a977f1af/runner-base-1.0-SNAPSHOT.jar";
        int pos = 19;
        for (File file : myfiles) {
            docArgs[pos] = file.getAbsolutePath();
            pos++;
        }
        for (File file : baseFiles) {
            docArgs[pos] = file.getAbsolutePath();
            pos++;
        }
        return docArgs;
    }
    
    private static List<File>  getBaseFiles(){
        File dataDir = new File("/Users/zhangchao/work/codespaces/runner-codes/Runner-Base/src/main/java/com/ai/runner/base/vo");
        // 存放目录及其子目录下的所有文件对象
        List<File> myfiles = new ArrayList<File>();
        // 开始遍历
        listDirectory(dataDir, myfiles);
        return myfiles;
    }

    private static void listDirectory(File path, List<File> myfile) {
        if (!path.exists()) {
            System.out.println("文件名称不存在!");
        } else {
            String p = path.getName();
            if (path.isFile()) {
                if (path.getName().indexOf(".ini") < 0)
                    myfile.add(path);
            } else {
                File[] files = path.listFiles();
                for (int i = 0; i < files.length; i++) {
                    listDirectory(files[i], myfile);
                }
            }
        }
    }

}
