FROM openjdk:11

USER root

ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=ma45lata
ENV MYSQL_URL=jdbc:mysql://tomcatdbhost/restapidemo?useUnicode=true&connectionCollation=utf8_general_ci&characterSetResults=utf8&characterEncoding=utf8&serverTimezone=UTC
ENV SERVER_BASE_DIR=/opt/tomcat
ENV SERVER_BASE_LOG_DIR=/opt/tomcat/logs
ENV CATALINA_OPTS=-agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n
ENV SERVER_BASE_DIR=/opt/tomcat
ENV SERVER_BASE_LOG_DIR=/opt/tomcat/logs

# crea la directory
RUN mkdir /opt/tomcat/
RUN mkdir /opt/tomcat/configuration/ && \
    mkdir /opt/tomcat/configuration/webapps/


RUN apt-get update && apt-get install vim openssl -y 

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

# ADD ./tomcat/conf/context.xml /opt/tomcat/conf/context.xml
# ADD ./tomcat/conf/server.xml /opt/tomcat/conf/server.xml
# ADD ./tomcat/conf/web.xml /opt/tomcat/conf/web.xml

ADD ./libs/log4j-1.2.17.jar /opt/tomcat/lib/log4j-1.2.17.jar
ADD ./libs/hibernate-search-engine-5.11.5.Final.jar /opt/tomcat/lib/hibernate-search-engine-5.11.5.Final.jar
ADD ./libs/hibernate-search-orm-5.11.5.Final.jar /opt/tomcat/lib/hibernate-search-orm-5.11.5.Final.jar

EXPOSE 8787

CMD ["/opt/tomcat/bin/catalina.sh", "run"]