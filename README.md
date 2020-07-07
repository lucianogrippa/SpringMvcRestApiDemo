## Description

The main goal of project is to create a basic REST API SERVICE with Java Spring Webmvc Framework which can be deployed on Java application servers (jboss wildfly 19.1.0.Final) and database servers (Mysql 5.7).<br />

The project includes:

- Spring Webmvc.
- Secured api request with jwt token in Authorization header (Spring Security).
- Data persistence with hibernate (Spring jpa and Hibernate).
- Exception handling and custom error output.
- Docker containers to develop application on the jboss server and database server.

### Environment

For simulate real environment is used [docker technology](https://www.docker.com), all file required for building it are in [/docker](/docker) folder.

![Docker compose services](/docs/images/docker-compose.PNG?raw=true "Project services")
<p>
As you see in above picture, the environment has 3 services <b>wildfly</b>, <b>appdb</b>, <b>phpmyadmin</b>.</p>

- **wildfly** service is [jboss wildfly 19.1.0.Final server](https://wildfly.org) running using [open-jdk](https://openjdk.java.net) 11 .
  it has 4 volumes mounted on following directories:
  - [standalone/configuration/webapp](/docker/wildfly/standalone/configuration/webapps)
    where is placed properties file for application .
  - [standalone/deployments](/docker/wildfly/standalone/deployments)
    where is placed the compiled application file **SpringRestApiDemo.war**.
  - [standalone/log](/docker/wildfly/standalone/log)
    where are placed server log and application log files.
  - [standalone/lib](/docker/wildfly/standalone/lib)
    where can be placed libraries for application dependencies.

  The connection with database is performed with datasource and mysql connector module configured on standalone.xml file. 
  The wildfly service is provided at localhost:8080.<br />
  
- **appdb** is [Mysql 5.7 server image](https://hub.docker.com/_/mysql) where founding schema "restapidemo".
  The schema is created from [mysql/dump.sql](/docker/mysql/dump.sql) file.
  The appdb service is provided at localhost: 3306, all connection parameters can be set up on [docker-compose.yml](/docker/docker-compose.yml) file.<br />

- **phpmyadmin** is simple web server php with phpmyadmin installed and ready to use.
  Can be useful for explore or manage database server. The phpmyadmin service is provided at localhost:8883.<br />

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
  
</p>

### Rest Service project

<p>There are 3 controllers, each of which can be reached via /api path.</p>

Each controller exposes a specific group of APIs as you can see on [json postman descriptor](/docs/SpringRestApiDemo.postman_collection.json) (you can import it in postman application).<br />

All basics services have been implemented in main path /api: <br>
<h5>AuthController</h5>
---

- GET: **/signin/{authToken}** : the path parameter **authToken** is created through the following function:

```java
  DigestUtils.sha256Hex(String.format("%s@%s@%s"), username,secret, appkey);
```
the service return jwt token String.<br /> AppKey is an application property. 

- POST: **/signin** : accept payload object with 3 fields: 

```json
 {
	"appKey":"f9e7bc9fcae0ffccf975708bdf40ab13",
	"username":"lgrippa",
	"secret":"48df8b3e62340b9cf63040d3318c8809"
 }
 ```
 and if authentication succeded return:
 ```json
 {
    "id": 1594128067680,
    "status": 200,
    "data": "{json token}"
}
```
return json token in data field.


<h5>ContentDemoController</h5>
---

- GET: **/echo/{id}** : is simple echo service.
- GET: **/testtoken** : used for testing purpose not expose it. returns a simple string to using as a parameter to GET /signin/{authToken} service.
- GET: **/genBCryptPassword** : returns the encrypted password to be set to basic authentication provider (see basicAuthManager in [SpringRestApiDemo-security.xml](src\main\webapp\WEB-INF\SpringRestApiDemo-security.xml) file) required to protect the swagger-ui.html page.

<h5>RolesController</h5>
---

- GET : **/role/{roleid}** : find role object by id.
- GET : **/rolecode/{code}** : find role object by code.
- GET : **/listroles** : list all roles.
- POST: **/saveRole** : save a role object.
- DELETE: **/deleteRole/{roleId}** : delete role object by id.

<h5>UserController</h5>
---

- GET : **/listusers** : list all users.
- POST: **/saveUser** : save an user.
- DELETE: **/deleteUser/{userId}** : delete user by id.

Each service is protected with jwt token provided by user authentication calling one of /signin/{authToken} or /signin services.
The jwt token must be put in "Authorization" header prefixed by "Bearer" string like this example:

```bash

$ curl --location --request GET 'http://localhost:8080/SpringRestApiDemo/api/echo/4' \
  --header 'Authorization: Bearer '

```

The token settings are provide by [application properties](/docker/wildfly/standalone/configuration/webapps/application.springrestdemo.properties) file.<br>

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

---

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

**[controllers](/src/main/java/controllers)**: contains all application controller:
- [AdviceExceptionHandlerController](/src/main/java/controllers/AdviceExceptionHandlerController.java), manages the exceptions for output presentation
- [AuthController](src/main/java/controllers/AuthController.java), manages the /signin api needed for creating jwt token involved in authentication process.
- [ContentDemoController](/src/main/java/controllers/ContentDemoController.java), contains simple "api" /echo and /testtoken
- [UserController](/src/main/java/controllers/UserController.java) , contains api for add, edit or list users.
- [RolesController](/src/main/java/controllers/RolesController.java) , contains api for add, edit or list roles.
- [HttpApiDefaultErrorPageController](/src/main/java/controllers/HttpApiDefaultErrorPageController.java), contains all error page controllers.

**[dao](/src/main/java/dao)**, contains all dao interfaces definition need for crud operations.<br />
**[dtos](/src/main/java/dtos)**, contains all classes to be serializing in json to output presentation layer.<br />
**[entities](/src/main/java/entities)**, contains map jpa entities classes.<br />
**[exceptions](/src/main/java/exceptions)**, exception classes implementations.<br />
**[helpers](/src/main/java/helpers)**,helpers classes useful for manage some situations as logging, get application properties or generate jwt token with specific parameters.<br />
**[repositories](/src/main/java/repositories)**, contains dao implementation classes.<br />
**[security](/src/main/java/security)**, contains all classes needed for handle the security providers and filters.<br />
All classes to manage the jwt token have been putting in [security/api](/src/main/java/security/api) folder.<br />
**[services](/src/main/java/services)** contains services classes.<br />

###### resources folder

This folder contains logback, log4j and persistence.xml configurations.

###### webapp folder

In [WEB-INF](/src/main/webapp/WEB-INF) are collect all configuration files for Spring beans , Spring security and servlet web.xml.<br />


[Back To The Top](#description)

## How To Use

The main project dependency is maven so first of all you need [to install maven](https://maven.apache.org/install.html) in your system.
After that You can [clone](https://github.com/lucianogrippa/SpringMvcRestApiDemo.git) or [download](https://github.com/lucianogrippa/SpringMvcRestApiDemo/archive/make-docs.zip) project from github, if you are choose to download, unzip it then open terminal in directory containing project.

type:

```bash
 # this generates the archetype files in the ../spring-rest-archetype directory, then installs them in the default catalog.
$ ./archetype.sh

# if won't to put archetypes files in ../spring-rest-archetype  you should use -a argument
$ ./archetype.sh -a /path_to_put_archetype
```
<p>
If all done well, you are ready to create own project from this one.
The fastest and simplest way is to use Eclipse or any other IDE, you should find the archetype in the local default catalog (usually located in ~/.m2 directory).
</p>

Once the project is created you can compile it using the command:
```bash
$ ./complile.sh
# for skipping tests, use argument -s
$ ./compile.sh -s
```
This command generates /target/[projectname].war file and then copy it in [deployments](/docker/wildfly/standalone/deployments) directory.

<h5>Docker Environment</h5>

To build all images just go in /docker directory and then type:
```bash
$ ./buils_start.sh
```
This command force "build" and then start all services defined in [docker-compose.yml](docker/docker-compose.yml), so you should use this command once or only if necessary because the images will lost all data.

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
