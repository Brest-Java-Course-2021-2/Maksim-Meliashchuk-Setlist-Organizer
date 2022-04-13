#!/bin/bash

set -e -x

cd rest-app
mvn spring-boot:start -Dspring-boot.run.profiles=dev,jpa
cd ../gatling-test
mvn gatling:test
cd ../rest-app
mvn spring-boot:stop
