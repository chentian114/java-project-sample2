package com.chen.sample2.gen.utils;

import com.chen.sample2.gen.GenTool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WebGen {
    /**
     * 功能：生成Dao类主体代码
     */
    public static void parse(String tableComment, String entityName, String priType) {
        String webName = entityName + "Controller";
        StringBuffer sb = new StringBuffer();
        sb.append("package " + GenTool.webPackageOutPath + ";\r\n");
        sb.append("\r\n");

        sb.append("import " + GenTool.basePackageOutPath + ".BaseController;\r\n");
        sb.append("import " + GenTool.entityPackageOutPath + "." + entityName + ";\r\n");
        sb.append("import " + GenTool.rootPackage + "tool.message.RequestMsg;\r\n");
        sb.append("import " + GenTool.rootPackage + "tool.message.ResponseMsg;\r\n");
        sb.append("import " + GenTool.svcPackageOutPath + ".I" + entityName + "Service;\r\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
        sb.append("import org.springframework.web.bind.annotation.*;\r\n\r\n");

        //注释部分
        sb.append("/**\r\n");
        sb.append(" * " + tableComment + " Controller\r\n");
        sb.append(" *\r\n");
        sb.append(" * @author " + GenTool.authorName + "\r\n");
        sb.append(" */ \r\n");

        sb.append("@RestController\r\n");
        sb.append("@RequestMapping(\"/" + GenTool.initcap1(entityName) + "\")\r\n");

        //实体部分
        sb.append("public class " + webName + " extends BaseController {\r\n\r\n");
        sb.append("\t@Autowired\r\n");
        sb.append("\tprivate I" + entityName + "Service " + GenTool.initcap1(entityName) + "Service;\r\n\r\n");

        sb.append("\t@PostMapping(\"/save\")\r\n");
        sb.append("\tpublic ResponseMsg save(@RequestBody " + entityName + " model) {\r\n");
        sb.append("\t\tResponseMsg responseMsg = new ResponseMsg();\r\n");
        sb.append("\t\t" + entityName + " result = " + GenTool.initcap1(entityName) + "Service.save(model);\r\n");
        sb.append("\t\tresponseMsg.setData(result);\r\n");
        sb.append("\t\treturn responseMsg;\r\n");
        sb.append("\t}\r\n\r\n");

        sb.append("\t@DeleteMapping(\"/deleteById/{id}\")\r\n");
        sb.append("\tpublic ResponseMsg deleteById(@PathVariable Integer id) {\r\n");
        sb.append("\t\tResponseMsg responseMsg = new ResponseMsg();\r\n");
        sb.append("\t\t" + GenTool.initcap1(entityName) + "Service.deleteById(id);\r\n");
        sb.append("\t\treturn responseMsg;\r\n");
        sb.append("\t}\r\n\r\n");

        sb.append("\t@GetMapping(\"/selectById/{id}\")\r\n");
        sb.append("\tpublic ResponseMsg selectById(@PathVariable Integer id) {\r\n");
        sb.append("\t\tResponseMsg responseMsg = new ResponseMsg();\r\n");
        sb.append("\t\t" + entityName + " result = " + GenTool.initcap1(entityName) + "Service.findById(id);\r\n");
        sb.append("\t\tresponseMsg.setData(result);\r\n");
        sb.append("\t\treturn responseMsg;\r\n");
        sb.append("\t}\r\n\r\n");

        sb.append("\t@GetMapping(\"/queryPage\")\r\n");
        sb.append("\tpublic ResponseMsg queryPage(Integer pageNumber,Integer pageSize) {\r\n");
        sb.append("\t\tRequestMsg requestMsg = new RequestMsg(pageNumber,pageSize);\r\n");
        sb.append("\t\treturn " + GenTool.initcap1(entityName) + "Service.queryPage(requestMsg);\r\n");
        sb.append("\t}\r\n\r\n");
        sb.append("}");

        try {
//            String path = GenTool.class.getClass().getResource("/").getPath().split("/target")[0];

            String path = System.getProperty("user.dir")+"/"+ GenTool.moduleName;
            String outputPath = path +"/src/main/java/" + GenTool.webPackageOutPath.replace(".", "/");
            File outPutFile = new File(outputPath);
            if (!outPutFile.exists()) {
                outPutFile.mkdirs();
            }
            String filePath = outputPath + "/" + webName + ".java";
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            pw.close();
            System.out.println("生成Controller: " + filePath.split("src/main/java/")[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
