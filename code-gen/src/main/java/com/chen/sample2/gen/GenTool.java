package com.chen.sample2.gen;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.chen.sample2.gen.dto.ColumnInfo;
import com.chen.sample2.gen.utils.DaoGen;
import com.chen.sample2.gen.utils.EntityGen;
import com.chen.sample2.gen.utils.ServiceGen;
import com.chen.sample2.gen.utils.WebGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 */
public class GenTool {
    private static Logger logger = LoggerFactory.getLogger(GenTool.class);

    public static String moduleName = "web";
    public static String rootPackage = "com.chen.sample2."; //基础package
    public static String modulePackage = rootPackage + moduleName+"."; //模块名
    public static String baseUrl ="BaseController.BaseNgrUrl" ; //controller的base路径
    public static String entityPackageOutPath = modulePackage + "entity";//指定实体生成所在包的路径
    public static String daoPackageOutPath = modulePackage + "dao";//指定Dao所在包的路径
    public static String svcPackageOutPath = modulePackage + "service";//指定service接口所在包的路径
    public static String svcImplPackageOutPath = modulePackage + "service.impl";//指定service实现所在包的路径
    public static String webPackageOutPath = modulePackage + "controller";//指定Controller实现所在包的路径
    public static String basePackageOutPath = modulePackage + "base"; //指定基础父类所在包的路径
    public static String authorName = "Chentian";//作者名字

    //数据库连接
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/sample?characterEncoding=UTF-8&autoReconnect=true&useUnicode=true&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&useSSL=false";
    private static final String NAME = "root";
    private static final String PASS = "root";
    private static final String TABLE_SCHEMA = "sample";
    private static final String COLUMN_SQL = "SELECT table_name AS tableName, column_name AS columnName, data_type AS dataType, " +
            "column_comment AS columnComment, column_key AS columnKey, extra AS extra FROM information_schema.columns " +
            "WHERE table_schema ='" + TABLE_SCHEMA + "' AND table_name = '%s' ORDER BY table_name ASC, ordinal_position ASC;";
    private static final String TABLE_SQL = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES " +
            "WHERE TABLE_SCHEMA='" + TABLE_SCHEMA + "' AND TABLE_TYPE='BASE TABLE' AND TABLE_NAME LIKE '%s';";

    public static void main(String[] args) throws Exception {
        gen("t_auth_role", true, true, true, true, "t_");
    }

    /**
     * 代码生成入口
     * @param tbName 表名，支持sql模糊匹配，通配符：%
     * @param isGenEntity 是否生成实体
     * @param isGenDao 是否生成Dao，为true时，isGenEntity也必须为true
     * @param isGenService 是否生成Service，为true时，isGenDao、sGenEntity也必须为true
     * @param isGenWeb 是否生成Controller，为true时，isGenService、isGenEntity也必须为true
     * @param prefix
     */
    public static void gen(String tbName, boolean isGenEntity, boolean isGenDao, boolean isGenService,
                           boolean isGenWeb, String prefix) {

        //查询数据库获取表的字段属性列表
        Map<String, Object> columnMap = queryColumnMapByDB(tbName);
        if(columnMap == null){
            logger.error("获取表的字段属性列表失败！");
            return;
        }

        for (Map.Entry<String, Object> map : columnMap.entrySet()) {
            JSONObject objects = JSONUtil.parseObj(JSONUtil.toJsonStr(map.getValue()));
            String tableName = map.getKey();
            String tableComment = objects.getStr("tableComment");
            String entityName = GenTool.initcap(subStrByPrefix(map.getKey(), prefix));
            String priType = "";
            if (isGenEntity) {
                priType = EntityGen.parse(tableName, tableComment, entityName, objects.getJSONArray("columnInfoList").toList(ColumnInfo.class));
            }
            if (isGenDao) {
                DaoGen.parse(tableComment, entityName);
            }
            if (isGenService) {
                ServiceGen.parse(tableComment, entityName);
            }
            if (isGenWeb) {
                WebGen.parse(tableComment, entityName, priType);
            }
        }
        logger.info("生成完毕！");
    }


    /**
     * 查询数据库获取表的字段属性列表
     */
    private static Map<String, Object> queryColumnMapByDB(String tbName) {
        //查要生成实体类的表
        String tableSql = String.format(TABLE_SQL, tbName);
        logger.info("查询表结构：{}", tableSql);
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        //创建连接
        try(Connection con = DriverManager.getConnection(URL, NAME, PASS)){
            PreparedStatement preStatement = null;
            preStatement = con.prepareStatement(tableSql);
            ResultSet tablesResultSet = preStatement.executeQuery();

            List<ColumnInfo> columnInfoList;
            Map<String, Object> columnMap = new HashMap<>();
            JSONObject tableJsonObject;
            while (tablesResultSet.next()) {
                String tName = tablesResultSet.getString(1);
                String tableComment = tablesResultSet.getString(2);
                String entitySql = String.format(COLUMN_SQL, tName);
                logger.info("查询表字段：{}, {}", tableComment, entitySql);
                preStatement = con.prepareStatement(entitySql);
                ResultSet columnResultSet = preStatement.executeQuery();
                tableJsonObject = new JSONObject();
                columnInfoList = new ArrayList<>();
                while (columnResultSet.next()) {
                    columnInfoList.add(new ColumnInfo(columnResultSet.getString(2), columnResultSet.getString(3), columnResultSet.getString(4), columnResultSet.getString(5), columnResultSet.getString(6)));
                }
                tableJsonObject.putOpt("columnInfoList", columnInfoList);
                tableJsonObject.putOpt("tableComment", tableComment);
                columnMap.put(tName, tableJsonObject);
            }
            return columnMap;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 功能：将输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    public static String initcap(String str) {
        String[] arr = str.split("_");
        String result = "";
        if (arr.length == 1) {
            return initcap0(str.toLowerCase());
        }
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                char[] ch = initcap0(arr[i].toLowerCase()).toCharArray();
                if (ch[0] >= 'a' && ch[0] <= 'z') {
                    ch[0] = (char) (ch[0] - 32);
                }
                result += new String(ch);
            } else {
                result += initcap0(arr[i].toLowerCase());
            }
        }
        return result;
    }

    /**
     * 功能：将输入字符串的首字母改成小写
     *
     * @param str
     * @return
     */
    public static String initcap2(String str) {
        String[] arr = str.split("_");
        String result = "";
        if (arr.length == 1) {
            return initcap1(str.toLowerCase());
        }
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                char[] ch = initcap0(arr[i].toLowerCase()).toCharArray();
                if (ch[0] >= 'A' && ch[0] <= 'Z') {
                    ch[0] = (char) (ch[0] + 32);
                }
                result += new String(ch);
            } else {
                result += initcap0(arr[i].toLowerCase());
            }
        }
        return result;
    }

    public static String initcap1(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char) (ch[0] + 32);
        }
        return new String(ch);
    }

    public static String initcap0(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    private static String subStrByPrefix(String str, String preFix) {
        return str.indexOf(preFix) == 0 ? str.substring(preFix.length()) : str;
    }
}
