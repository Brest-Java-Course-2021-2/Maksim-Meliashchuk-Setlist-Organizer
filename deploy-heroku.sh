#!/bin/bash

# Prepare Jar
mvn clean
mvn package

# Ensure, that docker-compose stopped
docker-compose stop

# Build new images
docker build ./rest-app -t getting-started
docker build ./web-app -t getting-started

# Start new deployment
cd rest-app
heroku container:push web --app setlist-organizer-rest
heroku container:release web --app setlist-organizer-rest
cd ../web-app
heroku container:push web --app setlist-organizer-web
heroku container:release web --app setlist-organizer-web
