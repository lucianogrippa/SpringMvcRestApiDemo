#!/bin/bash

# sets only if must build with specific version of jdk otherwise comment it
	# export JAVA_HOME=/home/lucianogrippa/sdk/jdk8u252-b09
	
MVNW_FILE=./mvnw
MVN_DIR=.mvn

# search  JAVA_HOME 
if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ];  then
    echo "JAVA_HOME found in $JAVA_HOME"    
else
    echo "no java_home environment settings found please set it"
fi


if [ ! -f "$MVNW_FILE" ] || [ ! -d "$MVN_DIR" ]; then
    echo "$MVNW_FILE does not exist"
    echo "create wrapper"
    exec mvn -N io.takari:maven:wrapper
fi

./mvnw -X test
