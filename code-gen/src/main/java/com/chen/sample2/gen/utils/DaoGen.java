package com.chen.sample2.gen.utils;

import com.chen.sample2.gen.GenTool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DaoGen {
    /**
     * 功能：生成Dao类主体代码
     */
    public static void parse(String tableComment, String entityName) {
        String daoName = entityName + "Dao";
        StringBuffer sb = new StringBuffer();
        sb.append("package " + GenTool.daoPackageOutPath + ";\r\n");
        sb.append("\r\n");

        sb.append("import " + GenTool.basePackageOutPath + ".BaseDao;\r\n");
        sb.append("import " + GenTool.entityPackageOutPath + "." + entityName + ";\r\n");
        sb.append("import org.springframework.stereotype.Repository;\r\n\r\n");

        //注释部分
        sb.append("/**\r\n");
        sb.append(" * " + tableComment + " Dao\r\n");
        sb.append(" *\r\n");
        sb.append(" * @author " + GenTool.authorName + "\r\n");
        sb.append(" */ \r\n");

        sb.append("@Repository\r\n");

        //实体部分
        sb.append("public interface " + daoName + " extends BaseDao<" + entityName + "> {\r\n\r\n");
        sb.append("}\r\n");

        try {
//            String path = GenTool.class.getClass().getResource("/").getPath().split("/target")[0];

            String path = System.getProperty("user.dir")+"/"+ GenTool.moduleName;
            String outputPath = path + "/src/main/java/" + GenTool.daoPackageOutPath.replace(".", "/");
            File outPutFile = new File(outputPath);
            if (!outPutFile.exists()) {
                outPutFile.mkdirs();
            }
            String filePath = outputPath + "/" + daoName + ".java";
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            pw.close();
            System.out.println("生成Dao: " + filePath.split("src/main/java/")[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
