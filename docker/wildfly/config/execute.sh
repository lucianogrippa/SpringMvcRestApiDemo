#!/bin/bash
JBOSS_HOME=/opt/jboss/wildfly
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh

JBOSS_PROFILE=standalone.xml

function wait_for_server() {
  until `$JBOSS_CLI -c "ls /deployment" &> /dev/null`; do
    sleep 1
  done
}

echo "=> Starting WildFly server"
$JBOSS_HOME/bin/standalone.sh -c $JBOSS_PROFILE > /dev/null &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Executing the commands: $1"
$JBOSS_CLI -c --file=/opt/config/$1

echo "=> Shutting down WildFly"
$JBOSS_CLI -c ":shutdown"
