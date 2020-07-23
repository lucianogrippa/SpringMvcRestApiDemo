FROM openjdk:11

USER root

# crea la directory
RUN mkdir /opt/tomcat/
RUN mkdir /opt/tomcat/configuration/ && \
    mkdir /opt/tomcat/configuration/webapps/


RUN apt-get update && apt-get install vim  -y 

ADD ./libs/apache-tomcat-8.5.57.tar.gz /opt/tomcat

# cre la working dir
WORKDIR /opt/tomcat

#RUN curl -O https://downloads.apache.org/tomcat/tomcat-8/v8.5.57/bin/apache-tomcat-8.5.57.tar.gz
# https://downloads.apache.org/tomcat/tomcat-8/v8.5.57/bin/apache-tomcat-8.5.57-deployer.tar.gz

#RUN tar xvfz apache*.tar.gz
RUN mv apache-tomcat-8.5.57/* /opt/tomcat/.

RUN rm -rf apache-tomcat-8.5.57

WORKDIR /opt/tomcat/webapps

ADD ./libs/mysql-connector-java-8.0.20.jar /opt/tomcat/lib/mysql-connector-java-8.0.20.jar
ADD ./mysql/context.xml /opt/tomcat/conf/context.xml
ADD ./mysql/server.xml /opt/tomcat/conf/server.xml
ADD ./libs/log4j-1.2.17.jar /opt/tomcat/lib/log4j-1.2.17.jar

ENV SERVER_BASE_DIR /opt/tomcat
ENV SERVER_BASE_LOG_DIR /opt/tomcat/logs

CMD ["/opt/tomcat/bin/catalina.sh", "run"]