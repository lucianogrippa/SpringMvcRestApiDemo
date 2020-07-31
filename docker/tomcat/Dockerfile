FROM openjdk:11

USER root

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

RUN export CATALINA_OPTS="-agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n";

CMD ["/opt/tomcat/bin/catalina.sh", "run"]