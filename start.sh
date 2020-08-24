#!/bin/bash

docker-compose -f docker/docker-compose.yml pull
docker-compose -f docker/docker-compose.yml rm -fsv
docker-compose -f docker/docker-compose.yml up