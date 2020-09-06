#!/bin/bash
DOCKER_CONFIGURATION_FILE=docker-compose-tomcat.yml

docker down
docker-compose -f $DOCKER_CONFIGURATION_FILE build --no-cache
docker-compose -f $DOCKER_CONFIGURATION_FILE up -d