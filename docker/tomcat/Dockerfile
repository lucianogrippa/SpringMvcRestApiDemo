FROM openjdk:11

# crea la directory
RUN mkdir /opt/tomcat/

# cre la working dir
WORKDIR /opt/tomcat
RUN curl -O https://downloads.apache.org/tomcat/tomcat-8/v8.5.57/bin/apache-tomcat-8.5.57.tar.gz
# https://downloads.apache.org/tomcat/tomcat-8/v8.5.57/bin/apache-tomcat-8.5.57-deployer.tar.gz

RUN tar xvfz apache*.tar.gz
RUN mv apache-tomcat-8.5.57/* /opt/tomcat/.

WORKDIR /opt/tomcat/webapps

CMD ["/opt/tomcat/bin/catalina.sh", "run"]