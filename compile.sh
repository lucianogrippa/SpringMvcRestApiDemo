#!/bin/bash

# sets only if must build with specific version of jdk otherwise comment it
export JAVA_HOME=/home/lucianogrippa/sdk/jdk8u252-b09

MVNW_FILE=./mvnw
MVN_DIR=.mvn


if [ ! -f "$MVNW_FILE" ] || [ ! -d "$MVN_DIR" ]; then
    echo "$MVNW_FILE does not exist"
    echo "create wrapper"
    exec mvn -N io.takari:maven:wrapper
fi

./mvnw clean install && echo "copy target/SpringRestApiDemo.war docker/deployments/" \
                     && cp target/SpringRestApiDemo.war docker/deployments/
