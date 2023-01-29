
<h1 align="center">
  <img src="https://user-images.githubusercontent.com/71301342/177768835-4f446d49-738d-406e-a3d4-1315e25f40b3.png" alt="Absurd" width="400">
  <br>Absurd Shopping Mall<br>
</h1>


<div align="center">
<img src="https://img.shields.io/badge/build-passing-brightgreen"/>
<img src="https://img.shields.io/badge/Java-1.8-blue"/>
<img src="https://img.shields.io/badge/Springboot-2.6.1-blue"/>
</div>
<div align="center">

  [English](README.md) | [简体中文](README.zh-CN.md)
</div>
<p align="center">
一个使用SpringBoot搭建的Javaweb电子商城。
</p>

<p align="center">
      <img width="850" src="https://user-images.githubusercontent.com/71301342/210303744-f0a7e434-fa37-4328-8000-564d6f234a2c.png" />
</p>



##  功能特性:
* 三个角色： 管理员、顾客和商家。
* 三个用户状态：注册、登录和注销。
* 六个页面：商品、购物车、商品管理、订单、用户管理和日志记录。
* 三个基本操作：增加、添加和更新。
* 一个统计：销量统计。
* 两个商品状态：上架和下架。
* 三个订单状态：提交、发货和确认收货。
* 两个日志记录：浏览和购买成功记录。
* 五个数据库表：log、order、storage、user和user_storage。(已经打包在Navicat_mysql_form文件夹下，需要预先安装Navicat，右键运行sql文件即可，连接到自己的数据库，然后选择该springboot-vue.sql文件运行即可)
* 订单发货时，平台邮件发送
* 平台自动处理资金流动

##  使用步骤：

### 0. 项目运行
 推荐使用idea
```
// 设置项目的  文件 -> 项目结构 -> 选择java sdk版本为1.8
cd ./springboot-vue-demo/vue
npm install

cd ./springboot-vue-demo/spring
mvn install
```
### 1. application的设置
```
cd ./spring/src/main/resources
vim application.properties
i
(按注释提示编辑）
:wq
vim application.yml
i
(按注释提示编辑）
:wq
```

### 2. 后端打包放上服务器
```
cd ./spring
mvn package 
```

### 3. 前端打包放上服务器
```
cd ./vue
npm run build
```
##  项目布局:
```
F:.
│  list.txt               //完整文件结构树
|  requirements.txt       //环境要求
│  README.md              //本文件
├─Navicat_mysql_form      //mysql数据库
│      springboot-vue.sql
│      
├─spring                    //后端代码
│  │  .gitignore
│  │  HELP.md
│  │  mvnw
│  │  mvnw.cmd
│  │  pom.xml
│  │  spring.iml
│  │      
│  ├─.mvn
│  │  └─wrapper
│  │          maven-wrapper.jar
│  │          maven-wrapper.properties
│  │          MavenWrapperDownloader.java
│  │          
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─com
│  │  │  │      └─example
│  │  │  │          └─spring
│  │  │  │              │  Application.java
│  │  │  │              │  
│  │  │  │              ├─common
│  │  │  │              │      CorsConfig.java
│  │  │  │              │      MybatisPlusConfig.java
│  │  │  │              │      Result.java
│  │  │  │              │      
│  │  │  │              ├─controller                    //控制者部分
│  │  │  │              │      FileController.java
│  │  │  │              │      LogController.java
│  │  │  │              │      MailController.java
│  │  │  │              │      OrderController.java
│  │  │  │              │      StorageController.java
│  │  │  │              │      UserController.java
│  │  │  │              │      User_StorageController.java
│  │  │  │              │      
│  │  │  │              ├─entity                        //javabean的实体类
│  │  │  │              │      Log.java
│  │  │  │              │      Order.java
│  │  │  │              │      Storage.java
│  │  │  │              │      User.java
│  │  │  │              │      User_Storage.java
│  │  │  │              │      
│  │  │  │              └─mapper                        //mybatis的mapper
│  │  │  │                      LogMappper.java
│  │  │  │                      OrderMapper.java
│  │  │  │                      StorageMapper.java
│  │  │  │                      UserMapper.java
│  │  │  │                      UserStorageMapper.java
│  │  │  │                      
│  │  │  └─resources
│  │  │      │  application.properties                  //数据库
│  │  │      │  application.yml                         //邮箱
│  │  │      │  
│  │  │      ├─files                                    //文件上传路径
│  │  │      ├─static
│  │  │      └─templates
│  │  └─test
│  │      └─java
│  │          └─com
│  │              └─example
│  │                  └─spring
│  │                          ApplicationTests.java
│                         
│                          
└─vue                                                   //前端
    │  
    ├─public
    │  │  favicon.ico
    │  │  index.html
    │  │  
    │  └─static
    │          config.js
    │          
    ├─src
    │  │  App.vue
    │  │  main.js
    │  │  
    │  ├─assets
    │  │  └─css
    │  │          global.css
    │  │          
    │  ├─components                 //头和侧边栏
    │  │      Aside.vue
    │  │      Header.vue
    │  │      
    │  ├─layout                     //基本底
    │  │      Layout.vue
    │  │      
    │  ├─router                     //网页结构
    │  │      index.js
    │  │      
    │  ├─store                      // route 
    │  │      index.js
    │  │      
    │  └─views                      //前端网页
    │          Cart.vue             //购物车
    │          Customer.vue         //顾客商品页
    │          Log.vue              //日志查看
    │          Login.vue            //登录
    │          Order.vue            //订单
    │          Person.vue           //个人主页
    │          Register.vue         //注册
    │          Storage.vue          //商品管理
    │          User.vue             //用户管理
    │          
```


