package com.chen.sample2.gen.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.chen.sample2.gen.GenTool;
import com.chen.sample2.gen.dto.ColumnInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ServiceGen {
    /**
     * 功能：生成Service主体代码
     */
    public static void parse(String tableComment, String entityName, List<ColumnInfo> columnInfoList) {
        String svcName = "I" + entityName + "Service";
        String svcImplName = entityName + "ServiceImpl";
        genSvc(tableComment, entityName, svcName);
        genSvcImpl(tableComment, entityName, svcName, svcImplName,columnInfoList);
    }

    /**
     * 生成Service接口
     *
     * @param svcName
     */
    private static void genSvc(String tableComment, String entityName, String svcName) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + GenTool.svcPackageOutPath + ";\r\n");
        sb.append("\r\n");

        sb.append("import " + GenTool.basePackageOutPath + ".BaseService;\r\n");
        sb.append("import " + GenTool.rootPackage + "tool.message.ResponseMsg;\r\n");
        sb.append("import " + GenTool.rootPackage + "tool.message.RequestMsg;\r\n");
        sb.append("import " + GenTool.entityPackageOutPath + "." + entityName + ";\r\n\r\n");

        //注释部分
        sb.append("/**\r\n");
        sb.append(" * " + tableComment + " 接口\r\n");
        sb.append(" *\r\n");
        sb.append(" * @author " + GenTool.authorName + "\r\n");
        sb.append(" */ \r\n");

        //实体部分
        sb.append("public interface " + svcName + " extends BaseService<" + entityName + "> {\r\n\r\n");
        sb.append("\tResponseMsg queryPage(RequestMsg requestMsg);\r\n");
        sb.append("}\r\n");

        try {
//            String path = GenTool.class.getClass().getResource("/").getPath().split("/target")[0];
            String path = System.getProperty("user.dir")+"/"+ GenTool.moduleName;
            String outputPath = path + "/src/main/java/" + GenTool.svcPackageOutPath.replace(".", "/");
            File outPutFile = new File(outputPath);
            if (!outPutFile.exists()) {
                outPutFile.mkdirs();
            }
            String filePath = outputPath + "/" + svcName + ".java";
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            pw.close();
            System.out.println("生成Service: " + filePath.split("src/main/java/")[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成Service实现类
     *
     * @param svcName
     * @param svcImplName
     */
    private static void genSvcImpl(String tableComment, String entityName, String svcName, String svcImplName, List<ColumnInfo> columnInfoList) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + GenTool.svcImplPackageOutPath + ";\r\n");
        sb.append("\r\n");

        sb.append("import " + GenTool.basePackageOutPath + ".BaseServiceImpl;\r\n");
        sb.append("import " + GenTool.rootPackage + "tool.message.ResponseMsg;\r\n");
        sb.append("import " + GenTool.rootPackage + "tool.message.RequestMsg;\r\n");
        sb.append("import " + GenTool.rootPackage + "tool.message.PageResult;\r\n");
        sb.append("import " + GenTool.rootPackage + "tool.persistence.SimpleCriteria;\r\n");
        sb.append("import " + GenTool.rootPackage + "tool.persistence.SimpleRestrictions;\r\n");
        sb.append("import " + GenTool.entityPackageOutPath + "." + entityName + ";\r\n");
        sb.append("import " + GenTool.svcPackageOutPath + "." + svcName + ";\r\n");
        sb.append("import " + GenTool.daoPackageOutPath + "." + entityName + "Dao;\r\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
        sb.append("import org.springframework.data.domain.PageRequest;\r\n");
        sb.append("import org.springframework.data.domain.Pageable;\r\n");
        sb.append("import org.springframework.data.domain.Page;\r\n");
        sb.append("import org.springframework.stereotype.Service;\r\n\r\n");


        //注释部分
        sb.append("/**\r\n");
        sb.append(" * " + tableComment + " 接口实现\r\n");
        sb.append(" *\r\n");
        sb.append(" * @author " + GenTool.authorName + "\r\n");
        sb.append(" */ \r\n");

        sb.append("@Service\r\n");
        //实体部分
        sb.append("public class " + svcImplName + " extends BaseServiceImpl<" + entityName + "> implements " + svcName + " {\r\n\r\n");
        sb.append("\t@Autowired\r\n");
        sb.append("\tprivate " + entityName + "Dao " + GenTool.initcap1(entityName) + "Dao;\r\n\r\n");

        sb.append("\t@Override\n");
        sb.append("\tpublic ResponseMsg queryPage(RequestMsg requestMsg){\r\n");
        sb.append("\t\t"+entityName+ " " + GenTool.initcap1(entityName) + " = requestMsg.getParams().toBean(" + entityName + ".class);\n");

        List<String> queryList = new ArrayList<>();
        for (ColumnInfo columnInfo : columnInfoList) {
            if(!"id".equals(columnInfo.getColumnName()) && !"uid".equals(columnInfo.getColumnName())
                && !"create_time".equals(columnInfo.getColumnName()) && !"update_time".equals(columnInfo.getColumnName())
                && !"modify_time".equals(columnInfo.getColumnName())){
                queryList.add("\t\t\t.add(SimpleRestrictions.eq(\""+GenTool.initcap2(columnInfo.getColumnName())+"\","+GenTool.initcap1(entityName)+".get"+GenTool.initcap0(columnInfo.getColumnName())+"()))\n");
            }
        }
        if(CollectionUtil.isNotEmpty(queryList)) {
            sb.append("\t\tSimpleCriteria<" + entityName + "> simpleCriteria = new SimpleCriteria.Builder<" + entityName + ">()\n");
            queryList.forEach(sb::append);
            sb.append("\t\t\t.builder();\n\n");
        }

        sb.append("\t\tPageable pageable = PageRequest.of(requestMsg.getPageNum(), requestMsg.getPageSize(),RequestMsg.DEFAULT_SORT);\n");
        sb.append("\t\tPage<"+entityName+"> page = " + GenTool.initcap1(entityName) + "Dao.findAll(simpleCriteria,pageable);\n\n");
        sb.append("\t\tPageResult<"+entityName+"> pageResult = new PageResult<>(page);\n");
        sb.append("\t\tlogger.info(\"pageResult:{}\", pageResult.toString());\n");
        sb.append("\t\treturn ResponseMsg.createSuccessResponse(pageResult);\n");
        sb.append("\t}\n");
        sb.append("}\r\n");

        try {
//            String path = GenTool.class.getClass().getResource("/").getPath().split("/target")[0];

            String path = System.getProperty("user.dir")+"/"+ GenTool.moduleName;
            String outputPath = path + "/src/main/java/" + GenTool.svcImplPackageOutPath.replace(".", "/") + "/";
            File outPutFile = new File(outputPath);
            if (!outPutFile.exists()) {
                outPutFile.mkdirs();
            }
            String filePath = outputPath + svcImplName + ".java";
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            pw.close();
            System.out.println("生成ServiceImpl: " + filePath.split("src/main/java/")[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
