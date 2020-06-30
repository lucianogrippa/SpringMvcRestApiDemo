#!/bin/sh

# -------------------------------------------------------------------------
# JBoss Bootstrap Script
# -------------------------------------------------------------------------
STANDALONE_FILE=$JBOSS_HOME/bin/standalone.sh
echo $STANDALONE_FILE

exec $STANDALONE_FILE -Djboss.server.home.dir=/opt/jboss/wildfly/standalone \
--debug 8794 \
-Djboss.server.log.dir=/opt/jboss/wildfly/standalone/log \
-b 0.0.0.0 \
-bmanagement 0.0.0.0 \
-c standalone.xml