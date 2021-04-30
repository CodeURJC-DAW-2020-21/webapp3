#!/bin/bash

# First, we locate the pom
cd ../backend || exit
# Executing dockerfile commands
sudo docker run --rm -v "$PWD":/data -w /data maven mvn package

# Copy the .jar files to docker dir
cp target/*.jar ../docker

# Return to docker folder
cd ../docker || exit

# Building the new container
sudo docker build -t dawequipo3/growing .
