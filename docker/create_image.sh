#!/bin/bash

# First, check the destination directory exists
if [ -d "../backend/src/main/resources/static/new" ]
then
# Delete all the inside content 
	rm -f ../backend/src/main/resources/static/new/*
else
# Create the folder
	mkdir ../backend/src/main/resources/static/new
fi

# Go to frontend which contains the Angular project
cd ../frontend

# Build the application 
ng build --prod --base-href="/new/"

# Copy all the dist folder content into backend static folder
cp dist/frontend/* ../backend/src/main/resources/static/new

# Then locate the pom.xml
cd ../backend || exit
# Executing dockerfile commands
sudo docker run --rm -v "$PWD":/data -w /data maven mvn package

# Copy the .jar files to docker dir
cp target/*.jar ../docker

# Return to docker folder
cd ../docker || exit

rm -f growing-0.0.1-SNAPSHOT.jar

# Building the new container
sudo docker build -t dawequipo3/growing .
