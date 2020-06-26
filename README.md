### Table of Contents
- [Description](#description)
  - [Environment](#environment)
  - [Database Structure](#database-structure)
  - [Rest Service](#rest-service)
- [How To Use](#how-to-use)
    - [Installation](#installation)
    - [API Reference](#api-reference)
- [References](#references)
- [License](#license)
- [Author Info](#author-info)

---

## Description

The main goal of project is create a basic rest API service in Java Spring webmvc Framework that can be used in environments builded on java application servers (wildfly) and databases server (Mysql 5.7).<br />

### Environment

For simulate real environment is used [docker technology](https://www.docker.com), all file required for building it are in [/docker](/docker) folder.

![Docker compose services](/docs/images/docker-compose.PNG?raw=true "Project services")

As you see in above picture, the environment has 3 services **wildfly**, **appdb**, **phpmyadmin**.<br />

- **wildfly** service is [jboss wildfly 10.1 server](https://wildfly.org) running using [open-jdk](https://openjdk.java.net) 11 .
  it has 4 volumes mounted on following directories:
  - [standalone/configuration/webapp](/docker/wildfly/standalone/configuration/webapps)
    where is placed properties file for application 
  - [standalone/deployments](/docker/wildfly/standalone/deployments)
    where is placed the compiled application file **SpringRestApiDemo.war**
  - [standalone/log](/docker/wildfly/standalone/log)
    where are placed server's log and application's log
  - [standalone/lib](/docker/wildfly/standalone/lib)
    where can be placed library for application dipendecies
Moreover is used standalone.xml file where is setting up a datasource required by 
our simple application.<br />
The wildfly service is provided at localhost:8080
  
- **appdb** is [Mysql 5.7 server image](https://hub.docker.com/_/mysql) where founding schema "restapidemo".
  The schema dump sql file is located on this file [dump.sql](/docker/mysql/dump.sql)
  The appdb service is provided at localhost: 3306, password and user can be setted in [docker-compose] (/docker/docker-compose.yml) file.

- **phpmyadmin** is simple web server php with phpmyadmin installed and ready to use.
  Can be useful for explore or manage database server.
  The phpmyadmin service is provided at localhost:8883

### Database Structure
![Docker compose services](/docs/images/database.png?raw=true "Project services")
The database is structurated in 3 tables (see pic above).

**roles -> usersroles -> users** each tables is mapped in [entities](/src/main/java/entities) clases.

As you can see this structure is designed to contain the user authentication information needed by the security framework to protect the controller /api and the same applies to all controllers.
If you wont to change structure or add or modifiy users or tables and consequently have the changes available for testing on the appdb service, you should edit the [dump.sql](/docker/mysql/dump.sql) file and remap the whole entity class.

Tables: 

- **roles** table contains roles definitions.
- **users** table contains users profiles information and authentication data.
- **usersroles** table defines relations "many to many" between users and roles.

You can perform all crud operations or search for queries using the [UserDao](/src/main/java/dao/UserDao.java) class.

### Rest Service
L'entry point del servizio è /api ogini richiesta deve essere autorizzata attraverso un token jwt che verrà rilasciato dopo 
l'autenticazione /api/signin.

[Back To The Top](#description)

## How To Use

#### Installation



#### API Reference

```html
    <p>dummy code</p>
```
[Back To The Top](##tableofcontents)

---

## References
[Back To The Top](#description)

---

## License

MIT License

Copyright (c) [2017] [James Q Quick]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

[Back To The Top](#description)

---

## Author Info

Luciano Grippa
- Twitter - [@lgrippa75](https://twitter.com/lgrippa75)

[Back To The Top](#description)
