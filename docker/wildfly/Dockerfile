# usa openjdk 11
FROM jboss/base-jdk:11

USER root

# imposta WILDFLY_VERSION

ENV WILDFLY_VERSION=19.1.0.Final
ENV WILDFLY_SHA1=6883125745a62b598659ea351f5b1706aff53955
ENV JBOSS_HOME=/opt/jboss/wildfly

ENV app.profile=wildfly
ENV spring.profiles.active=wildfly

ENV SERVER_BASE_DIR=/opt/jboss/wildfly/standalone
ENV SERVER_BASE_LOG_DIR=/opt/jboss/wildfly/log

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
ADD ./config /opt/config

USER root

RUN yum install telnet -y
RUN yum install mysql -y
RUN yum install git -y
RUN chmod +x /opt/config/execute.sh

RUN /opt/config/execute.sh commands.cli
RUN rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history

# elimina l'history dello standalone
RUN rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history


# aggiungi script per lanciare il server
ADD ./config/run.sh /opt/jboss/wildfly/bin/run.sh
ADD ./config/run-debug.sh /opt/jboss/wildfly/bin/run-debug.sh

USER root

RUN chmod u+x /opt/jboss/wildfly/bin/run.sh
RUN chmod u+x /opt/jboss/wildfly/bin/run-debug.sh

# run jboss
CMD ["/opt/jboss/wildfly/bin/run-debug.sh"]
