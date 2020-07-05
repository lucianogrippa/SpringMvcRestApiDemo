### Table of Contents
- [Description](#description)
  - [Environment](#environment)
  - [Database Structure](#database-structure)
  - [Rest Service project](#rest-service-project)
    - [Output json](#output-json)
    - [Folders organization](#folders-organization)
      - [docker](#docker)
      - [main](#main)
        - [java folder](#java-folder)
        - [resources folder](#resources-folder)
        - [webapp folder](#webapp-folder)
- [How To Use](#how-to-use)
- [Author Info](#author-info)

---

## Description

The main goal of project is create a basic rest API service in Java Spring webmvc Framework that can be used in environments builded on java application servers (jboss wildfly 19.1.0.Final) and databases server (Mysql 5.7).<br />

The project include:

- Spring Webmvc
- Secure api request with jwt token in Authorizzazion header (Spring Security)
- data persistance with hibernate (Spring jpa and Hibernate)
- swagger for api documentation 
- Exception handling and error custom output
- docker containers for simulate enviorment

### Environment

For simulate real environment is used [docker technology](https://www.docker.com), all file required for building it are in [/docker](/docker) folder.

![Docker compose services](/docs/images/docker-compose.PNG?raw=true "Project services")
<p>
As you see in above picture, the environment has 3 services <b>wildfly</b>, <b>appdb</b>, <b>phpmyadmin</b>.</p>

- **wildfly** service is [jboss wildfly 19.1.0.Final server](https://wildfly.org) running using [open-jdk](https://openjdk.java.net) 11 .
  it has 4 volumes mounted on following directories:
  - [standalone/configuration/webapp](/docker/wildfly/standalone/configuration/webapps)
    where is placed properties file for application 
  - [standalone/deployments](/docker/wildfly/standalone/deployments)
    where is placed the compiled application file **SpringRestApiDemo.war**
  - [standalone/log](/docker/wildfly/standalone/log)
    where are placed server log and application log
  - [standalone/lib](/docker/wildfly/standalone/lib)
    where can be placed libraries for application dependencies.

  jboss wildfly use standalone.xml configuration file where is setting up a datasource required by 
  our simple application.<br />
  The wildfly service is provided at localhost:8080
  
- **appdb** is [Mysql 5.7 server image](https://hub.docker.com/_/mysql) where founding schema "restapidemo".
  The schema dump sql file is located on this file [dump.sql](/docker/mysql/dump.sql)
  The appdb service is provided at localhost: 3306, password and user can be setted in [docker-compose.yml](/docker/docker-compose.yml) file.

- **phpmyadmin** is simple web server php with phpmyadmin installed and ready to use.
  Can be useful for explore or manage database server.
  The phpmyadmin service is provided at localhost:8883

### Database Structure

![Docker compose services](/docs/images/database.png?raw=true "Project services")
<p>
The database is structured in 3 tables.

**roles -> usersroles -> users** each tables is mapped in [entities](/src/main/java/entities) clases.

As you can see this structure is designed to contain the user authentication information needed by the security framework to protect the controller /api and the same applies to all controllers.
If you want to change structure , add or modify users or tables and consequently getting changes available for testing the "appdb" service, you should edit the [dump.sql](/docker/mysql/dump.sql) file and remap the whole entity class.

Tables: 

- **roles** table contains roles definitions.
- **users** table contains users profiles information and authentication data.
- **usersroles** table defines relations "many to many" between users and roles.

You can perform all crud operations or search for queries, using the [UserDao](/src/main/java/dao/UserDao.java) class, implementation is in [UserRepository](/src/main/java/repositories/UserRepository.java) class.
</p>

### Rest Service project

All basics services have been implemented in main path /api: <br>
- GET: **/test/{id}** 
- GET: **/testtoken**
- GET: **/signin/{authToken}**
- POST: **/signin**

Each service is protected by jwt token that is get through user authentication.
All token settings are provide by [application properties](/docker/wildfly/standalone/configuration/webapps/application.springrestdemo.properties) file.<br>

Properties file contain the following keys:
<br >
<h5>For jwt token</h5>

- jwt.secret : token secret
- jwt.issuer : issuer
- jwt.kid : token id
- jwt.auth.audience : audience
- jwt.expire.seconds : expire time in seconds
- jwt.expire.past.seconds : expire time for past in seconds

<h5>For another's purposes</h5>

- app.key : needed for authentication
- app.user.id : user id for testing apis

#### Output json

All controllers response with json representation of class [Content](/src/main/java/dtos/Content.java):

Success:

```json
  {
    "id": 24,
    "description": "That's works",
    "status": 200,
    "data": "paramiter: 24"
  }
```

Error:

```json
  {
    "id": 24,
    "description": "error description",
    "error":"error code or specific description",
    "status": 401
  }
```

The exceptions will respond with json representation of class [ApiErrorMessageResponse](/src/main/java/dtos/ApiErrorMessageResponse.java)


```json
  {
    "id": 1593784588022,
    "description": "Not Found",
    "status": 404,
    "error": "API_RESOURCE_NOT_FOUND",
    "exceptionOccurred": true
  }
```

The error pages are json output managed by [HttpApiDefaultErrorPageController](/src/main/java/controllers/HttpApiDefaultErrorPageController.java) controller

The exception will set different status and are managed by [AdviceExceptionHandlerController](/src/main/java/controllers/AdviceExceptionHandlerController.java) controller


#### Folders organization

##### [docker](/docker)

In this folder are located all files used for handle docker environments implementation.

##### [main](/src/main) 

this contains 3 folders java, resources,webapp

###### java folder

**[controllers](/src/main/java/controllers)**: contains all application controller in particular:
- [AdviceExceptionHandlerController](/src/main/java/controllers/AdviceExceptionHandlerController.java), manages the exceptions for output presentation
- [AuthController](src/main/java/controllers/AuthController.java), manages the /signin api needed for creating jwt token.
- [ContentDemoController](/src/main/java/controllers/ContentDemoController.java), contains simple "api" /echo and /testtoken
- [HttpApiDefaultErrorPageController](/src/main/java/controllers/HttpApiDefaultErrorPageController.java), contains all error page controllers.

**[dao](/src/main/java/dao)**, contains all dao interfaces definition need for crud operations.
**[dtos](/src/main/java/dtos)**, contains all classes to be serializing in json to output presentation layer.
**[entities](/src/main/java/entities)**, contains map jpa entities classes.
**[exceptions](/src/main/java/exceptions)**, exception classes implementations.
**[helpers](/src/main/java/helpers)**,helpers classes useful for manage some situations as logging, get application properties or generate jwt token with specific parameters.
**[repositories](/src/main/java/repositories)**, contains dao implementation classes.
**[security](/src/main/java/security)**, contains all classes needed for handle the security providers and filters.
All classes to manage the jwt token have been putting in [security/api](/src/main/java/security/api) folder.
**[services](/src/main/java/services)** contains services classes.

###### resources folder

This folder contains logback, log4j and persistence.xml configurations

###### webapp folder

In [WEB-INF](/src/main/webapp/WEB-INF) are collect all configuration files for Spring beans , Spring security and servlet web.xml.


[Back To The Top](#description)

## How To Use

The main project dependency is maven so first of all you need [to install maven](https://maven.apache.org/install.html) in your system.

You can use this project from maven archetype.<br>
First you should install install archetype.
[Download this project](https://github.com/lucianogrippa/SpringMvcRestApiDemo/archive/master.zip) from github unzip it and open terminal in directory containing project.

type:
```bash
# this generateing the archetype files in ../spring-rest-archetype directory
# and install it in default catalog.
$ ./archetype.sh

# if won't to put archetypes files in ../spring-rest-archetype  you should use -a argument
$ ./archetype.sh -a [archetype-generated-path]
```
<p>
If all done well, you are ready to create own project from this one.
The fastest and simplest way is to use Eclipse or any other IDE, you should find the archetype in the local default catalog (usually located in ~/.m2 directory).
</p>

Once the project is created you can compile it using the command:
```bash
$ ./complile.sh
# for skiping test just use argument -s
$ ./compile.sh -s
```
This command generates /target/[projectname].war file and then copy it in [deployments](/docker/wildfly/standalone/deployments) directory.

<h5>Docker Environment</h5>

To build all images just go in /docker directory and then type:
```bash
$ ./buils_start.sh
```
This command force "build" and then start all service defined in [docker-compose.yml](docker/docker-compose.yml), so you should use this command once or only if necessary because the images will lost all data.

If the images exists you can control start and stop using the commands:

```bash
# start the dockers services.
$ ./start.sh
 #stop docker services
$ ./stop.sh
```

[Back To The Top](##tableofcontents)


---

## Author Info

Luciano Grippa
- Twitter - [@lgrippa75](https://twitter.com/lgrippa75)
- skype - lucianogrippa

[Back To The Top](#description)
