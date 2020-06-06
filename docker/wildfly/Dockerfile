FROM jboss/wildfly:9.0.2.Final

# adds a management user with the following credentials: admin:admin
# if you want to add an application user, use the '-a' option
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

ADD jdbc /opt/jdbc
ADD config /opt/config
ADD amq /opt/amq

USER root
RUN chmod +x /opt/config/execute.sh
USER jboss

RUN /opt/config/execute.sh commands.cli
RUN unzip /opt/amq/activemq-rar-5.10.0.rar -d /opt/jboss/wildfly/modules/org/apache/activemq/main

RUN rm -f /opt/jboss/wildfly/modules/org/apache/activemq/main/broker-config.xml
RUN rm -f /opt/jboss/wildfly/modules/org/apache/activemq/main/log4j*
RUN rm -f /opt/jboss/wildfly/modules/org/apache/activemq/main/slf4j*
RUN rm -f /opt/jboss/wildfly/modules/org/apache/activemq/main/geronimo*

# cleanup e aggiungi il modulo che gestisce le code
RUN rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history
RUN /opt/config/execute.sh add-activemq-rar.cli

# elimina l'history dello standalone
RUN rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history

# aggiungi script per lanciare il server
ADD ./config/run.sh /opt/jboss/wildfly/bin/run.sh
ADD ./config/run-debug.sh /opt/jboss/wildfly/bin/run-debug.sh

# aggiungi alla configurazione le properties
#ADD ./config/application.springrestdemo.properties $JBOSS_HOME/standalone/configuration/application.springrestdemo.properties
# run jboss
CMD ["/opt/jboss/wildfly/bin/run-debug.sh"]
