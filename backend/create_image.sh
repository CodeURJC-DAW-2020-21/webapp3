#!/bin/bash

mkdir growing
cd growing || exit
git clone https://github.com/CodeURJC-DAW-2020-21/webapp3
cd webapp3/backend || exit
cd docker || exit
sudo docker-compose up -d