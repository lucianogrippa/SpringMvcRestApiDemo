#!/bin/bash
DOCKER_CONFIGURATION_FILE=docker-compose-tomcat.yml

docker rm -f $(docker ps -aq)
docker-compose -f $DOCKER_CONFIGURATION_FILE build --no-cache
docker-compose -f $DOCKER_CONFIGURATION_FILE up