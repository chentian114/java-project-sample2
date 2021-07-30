# Java 工程模板

## 目的

构建基础工程开发模板，用于快速搭建工程项目。

## 适用说明

适用于快速构建Java单体应用开发项目。

## 模块说明
- tool-common 公共组件模块
- web 服务模块
- code-gen 代码生成器模块
  - 支持连接数据库，生成 Controller,Service,ServiceImpl,Dao,Entity 各层代码；
  - 支持表模糊匹配，匹配生成代码；
  - 支持生成指定层次代码。
- scripts 相关脚本

## 项目框架
- springBoot2
- spring data jpa

## 中间件依赖
- mysql
- redis

## 项目构建

maven