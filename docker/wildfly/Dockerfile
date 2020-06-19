# usa openjdk 11
FROM jboss/base-jdk:11

# imposta WILDFLY_VERSION
ENV WILDFLY_VERSION 19.1.0.Final
ENV WILDFLY_SHA1 6883125745a62b598659ea351f5b1706aff53955
ENV JBOSS_HOME /opt/jboss/wildfly

USER root

# fai il download dell'immagine jboss
RUN cd $HOME \
    && curl -O https://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz \
    && sha1sum wildfly-$WILDFLY_VERSION.tar.gz | grep $WILDFLY_SHA1 \
    && tar xf wildfly-$WILDFLY_VERSION.tar.gz \
    && mv $HOME/wildfly-$WILDFLY_VERSION $JBOSS_HOME \
    && rm wildfly-$WILDFLY_VERSION.tar.gz \
    && chown -R jboss:0 ${JBOSS_HOME} \
    && chmod -R g+rw ${JBOSS_HOME}

# aggiungi le credenziali jsboss per permettere l'uso del cli
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

ADD jdbc /opt/jdbc
ADD config /opt/config
# ADD amq /opt/amq

USER root

RUN yum install telnet -y
RUN yum install mysql -y
RUN chmod +x /opt/config/execute.sh
USER jboss

RUN /opt/config/execute.sh commands.cli

# non installa re il server di code attualmente non serve al progetto
# RUN unzip /opt/amq/activemq-rar-5.10.0.rar -d /opt/jboss/wildfly/modules/org/apache/activemq/main

# RUN rm -f /opt/jboss/wildfly/modules/org/apache/activemq/main/broker-config.xml
# RUN rm -f /opt/jboss/wildfly/modules/org/apache/activemq/main/log4j*
# RUN rm -f /opt/jboss/wildfly/modules/org/apache/activemq/main/slf4j*
# RUN rm -f /opt/jboss/wildfly/modules/org/apache/activemq/main/geronimo*

# cleanup e aggiungi il modulo che gestisce le code
RUN rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history
# RUN /opt/config/execute.sh add-activemq-rar.cli

# elimina l'history dello standalone
RUN rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history

# aggiungi script per lanciare il server
ADD ./config/run.sh /opt/jboss/wildfly/bin/run.sh
ADD ./config/run-debug.sh /opt/jboss/wildfly/bin/run-debug.sh

USER root

RUN chmod u+x /opt/jboss/wildfly/bin/run.sh
RUN chmod u+x /opt/jboss/wildfly/bin/run-debug.sh

# aggiungi alla configurazione le properties
#ADD ./config/application.springrestdemo.properties $JBOSS_HOME/standalone/configuration/application.springrestdemo.properties
# run jboss
CMD ["/opt/jboss/wildfly/bin/run-debug.sh"]
