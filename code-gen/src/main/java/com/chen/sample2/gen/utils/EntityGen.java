package com.chen.sample2.gen.utils;

import com.chen.sample2.gen.GenTool;
import com.chen.sample2.gen.dto.ColumnInfo;
import com.chen.sample2.gen.dto.PrimaryKeyDto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EntityGen {
    /**
     * 功能：生成实体类主体代码
     *
     * @param tablename
     * @param columnInfoList
     */
    public static PrimaryKeyDto parse(String tablename, String tableComment, String entityName, List<ColumnInfo> columnInfoList) {
        String primaryType = "";
        String primaryName = "";

        StringBuffer sb = new StringBuffer();
        boolean isDate = false;
        boolean isSql = false;
        StringBuffer attrSB = new StringBuffer();
        StringBuffer methodSB = new StringBuffer();
        boolean isUUID = false;
        for (ColumnInfo columnInfo : columnInfoList) {

            attrSB.append("\t/**\r\n");
            attrSB.append("\t * " + columnInfo.getColumnComment() + "\r\n");
            attrSB.append("\t */ \r\n");

            String columnType = sqlType2JavaType(columnInfo.getDataType());
            if (columnInfo.getColumnKey().equals("PRI")) {
                attrSB.append("\t@Id\r\n");
                if (columnInfo.getExtra().equals("auto_increment")) {
                    attrSB.append("\t@GeneratedValue(strategy = GenerationType.IDENTITY)\r\n");
                } else {
                    attrSB.append("\t@GeneratedValue(generator = \"jpa-uuid\")\r\n");
                    isUUID = true;
                }
                primaryType = columnType;
                primaryName = GenTool.initcap2(columnInfo.getColumnName()) ;
            }
            if (columnInfo.getDataType().equalsIgnoreCase("datetime")
                || columnInfo.getDataType().equalsIgnoreCase("timestamp")) {
                isDate = true;
            }
            if (columnInfo.getDataType().equalsIgnoreCase("image")
                    || columnInfo.getDataType().equalsIgnoreCase("text")) {
                isSql = true;
            }

            attrSB.append("\t@Column(name = \"" + columnInfo.getColumnName() + "\")\r\n");
            attrSB.append("\tprivate " + columnType + " " + GenTool.initcap2(columnInfo.getColumnName()) + ";\r\n\r\n");

            methodSB.append("\tpublic " + columnType + " get" + GenTool.initcap(columnInfo.getColumnName()) + "(){\r\n");
            methodSB.append("\t\treturn " + GenTool.initcap2(columnInfo.getColumnName()) + ";\r\n");
            methodSB.append("\t}\r\n\r\n");
            methodSB.append("\tpublic void set" + GenTool.initcap(columnInfo.getColumnName()) + "(" + columnType + " " + GenTool.initcap2(columnInfo.getColumnName()) + "){\r\n");
            methodSB.append("\t\tthis." + GenTool.initcap2(columnInfo.getColumnName()) + "=" + GenTool.initcap2(columnInfo.getColumnName()) + ";\r\n");
            methodSB.append("\t}\r\n\r\n");
        }

        sb.append("package " + GenTool.entityPackageOutPath + ";\r\n");
        sb.append("\r\n");

        sb.append("import lombok.*;\r\n\r\n");
        sb.append("import javax.persistence.*;\r\n");
        //判断是否导入工具包
        if (isDate) {
            sb.append("import java.util.Date;\r\n");
        }
        if (isSql) {
            sb.append("import java.sql.*;\r\n");
        }
        if(isUUID){
            sb.append("import org.hibernate.annotations.GenericGenerator;\n");
        }
        sb.append("import java.io.Serializable;\r\n\r\n");


        //注释部分
        sb.append("/**\r\n");
        sb.append(" * " + tableComment + "\r\n");
        sb.append(" *\r\n");
        sb.append(" * @author " + GenTool.authorName + "\r\n");
        sb.append(" */ \r\n");

        sb.append("@NoArgsConstructor\r\n");
        sb.append("@AllArgsConstructor\r\n");
        sb.append("@Data\r\n");
        sb.append("@Builder\r\n");
        sb.append("@ToString\r\n");
        sb.append("@Entity\r\n");
        sb.append("@Table(name = \"" + tablename + "\")\r\n");
        if (isUUID) {
            sb.append("@GenericGenerator(name = \"jpa-uuid\", strategy = \"uuid\")\r\n");
        }

        //实体部分
        sb.append("public class " + entityName + " implements Serializable {\r\n\r\n");
        sb.append(attrSB.toString());
//        sb.append(methodSB.toString());
        sb.append("}\r\n");

        //System.out.println(sb.toString());
        try {
//            String path = GenTool.class.getClass().getResource("/").getPath().split("/target")[0];

            String path = System.getProperty("user.dir")+"/"+ GenTool.moduleName;

            String outputPath = path + "/src/main/java/" + GenTool.entityPackageOutPath.replace(".", "/");
            File outPutFile = new File(outputPath);
            if (!outPutFile.exists()) {
                outPutFile.mkdirs();
            }
            String filePath = outputPath + "/" + entityName + ".java";
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(sb.toString());
            pw.flush();
            pw.close();
            System.out.println("生成Entity: " + filePath.split("src/main/java/")[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PrimaryKeyDto(primaryType,primaryName,isUUID);
    }

    /**
     * 功能：获得列的数据类型
     *
     * @param sqlType
     * @return
     */
    public static String sqlType2JavaType(String sqlType) {

        if (sqlType.equalsIgnoreCase("bit")) {
            return "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "Byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "Short";
        } else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("INT UNSIGNED")) {
            //INT UNSIGNED无符号整形
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "Float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("double") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("timestamp")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blod";
        }
        return null;
    }
}
