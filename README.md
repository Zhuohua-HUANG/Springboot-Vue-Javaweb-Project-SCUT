
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
A Springboot-Vue Javaweb Project. Made by Zhuohua Huang.
</p>

<p align="center">
      <img width="850" src="https://user-images.githubusercontent.com/71301342/210303744-f0a7e434-fa37-4328-8000-564d6f234a2c.png" />
</p>



##  Features:
* Three roles： administrator, customers, and storeowners;

* Three user states: registration, login, and logout;

* Six pages: Merchandise, Shopping Cart, Merchandise Management, Orders, User Management, and logging;

* Three basic operations: Add, delete, and update;

* One statistic: sales statistics;

* Two product states: on shelves, and off shelves;

* Three order states: Submit, ship, and confirm receipt;

* Two log records: browsing records, and purchase success records;

* Five database tables: log, order, storage, user, and user_storage. (Navicat has been packaged in the Navicat_mysql_form folder. You need to install Navicat. Right-click and run the sql file, connect to your database, and select the springboot-vue.sql file to run.);

* Platform email will be sent when the order is shipped;

* The platform automatically manage money flows;

##  How to use：

### 0. Run Project:
Idea is recommended
```
// open Idea "file" -> "project structure" -> set java sdk version to 1.8
cd ./springboot-vue-demo/vue
npm install

cd ./springboot-vue-demo/spring
mvn install
```
### 1. Set Application Files
```
cd ./spring/src/main/resources
vim application.properties
i
(as prompted by the comments）
:wq
vim application.yml
i
(as prompted by the comments）
:wq
```

### 2. Package the Backend and Put on Server
```
cd ./spring
mvn package 
```

### 3. Package the Frontend and Put on Server
```
cd ./vue
npm run build
```
##  File Tree:
```
F:.
│  list.txt               //Complete file structure tree
|  requirements.txt       //environmental requirements
│  README.md              //this file
│  README.zh-CN.md        //chinese version of this file
├─Navicat_mysql_form      //mysql database
│      springboot-vue.sql
│      
├─spring                    //backend code
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
│  │  │  │              ├─controller                    //controllers
│  │  │  │              │      FileController.java
│  │  │  │              │      LogController.java
│  │  │  │              │      MailController.java
│  │  │  │              │      OrderController.java
│  │  │  │              │      StorageController.java
│  │  │  │              │      UserController.java
│  │  │  │              │      User_StorageController.java
│  │  │  │              │      
│  │  │  │              ├─entity                        //the entities of javabean
│  │  │  │              │      Log.java
│  │  │  │              │      Order.java
│  │  │  │              │      Storage.java
│  │  │  │              │      User.java
│  │  │  │              │      User_Storage.java
│  │  │  │              │      
│  │  │  │              └─mapper                        //mappers of mybatis
│  │  │  │                      LogMappper.java
│  │  │  │                      OrderMapper.java
│  │  │  │                      StorageMapper.java
│  │  │  │                      UserMapper.java
│  │  │  │                      UserStorageMapper.java
│  │  │  │                      
│  │  │  └─resources
│  │  │      │  application.properties                  //database settings
│  │  │      │  application.yml                         //email location
│  │  │      │  
│  │  │      ├─files                                    //file upload path
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
└─vue                                                   //front end
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
    │  ├─components                 //header and aside of the front end
    │  │      Aside.vue
    │  │      Header.vue
    │  │      
    │  ├─layout                     //layout
    │  │      Layout.vue
    │  │      
    │  ├─router                     //structure of the page
    │  │      index.js
    │  │      
    │  ├─store                      // route 
    │  │      index.js
    │  │      
    │  └─views                      //frontend pages
    │          Cart.vue             //shopping cart
    │          Customer.vue         //customer merchandise page
    │          Log.vue              //check the logs
    │          Login.vue            //login page
    │          Order.vue            //check orders
    │          Person.vue           //personal page
    │          Register.vue         //register page
    │          Storage.vue          //commodity management
    │          User.vue             //user management
    │          
```

