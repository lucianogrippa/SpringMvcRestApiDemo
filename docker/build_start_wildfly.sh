#!/bin/bash

#########################
# clean up images
########################
docker down
docker rm -f wildfly
docker rm -f wildfly-db
docker rm -f myadmin-wildfly

##########################
# CREATE IMAGE
##########################
docker-compose build --no-cache
docker-compose up -d