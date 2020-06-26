### Table of Contents
- [Description](#description)
    - [Technologies](#technologies)
- [How To Use](#how-to-use)
    - [Installation](#installation)
    - [API Reference](#api-reference)
- [References](#references)
- [License](#license)
- [Author Info](#author-info)

---

## Description

The main goal of the project is to create a basic rest API service in Java Spring webmvc Framework that can be used in environments builded with application server (wildfly) and database server (Mysql 5.7).<br />
To simulate the environment was used docker technology.
<br />All files for "docker" implementation are in [docker](/docker) folder.

![Docker compose services](/docs/images/docker-compose.PNG?raw=true "Project services")

As you see in above picture, the environment has 3 services wildfly,appdb,phpmyadmin.<br />

- wildfly service is jboss wildfly 10.1 server running using open-jdk 11 .
  it has 4 volumes mounted on following directories:
  - [standalone/configuration/webapp](/docker/wildfly/standalone/configuration/webapps)
    where is placed properties file for application 
  - [standalone/deployments](/docker/wildfly/standalone/deployments)
    where is placed the compiled application file **SpringRestApiDemo.war**
  - [standalone/log](/docker/wildfly/standalone/log)
    where are placed server's log and application's log
  - [standalone/lib](/docker/wildfly/standalone/lib)
    where can be placed library for application dipendecies
  
- 

#### Technologies

- Technology 1
- Technology 2

[Back To The Top](#technologies)

---

## How To Use

#### Installation



#### API Reference

```html
    <p>dummy code</p>
```
[Back To The Top](#read-me-template)

---

## References
[Back To The Top](#read-me-template)

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

[Back To The Top](#read-me-template)

---

## Author Info

- Twitter - [@lgrippa75](https://twitter.com/lgrippa75)

[Back To The Top](#read-me-template)
