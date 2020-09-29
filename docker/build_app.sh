#!/bin/bash

#########################
# clean up images
########################
docker-compose rm -f tomcatapp
docker-compose rm -f wildfly
docker-compose rm -f appdb
docker-compose rm -f phpmyadmin
docker-compose rm -f git-server
docker-compose rm -f springboot 

##########################
# CREATE IMAGE
##########################
docker-compose build --no-cache
docker-compose up -d
