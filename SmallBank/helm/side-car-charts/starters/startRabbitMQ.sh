#!/bin/bash

echo "###################################################################"
echo "Building and starting RabbitMQ"
cd ../rabbitmq
echo "Inside folder $PWD, building RabbitMQ helm chart"
helm dependencies build

cd ../
echo "Inside folder $PWD, starting RabbitMQ using helm"
helm install rabbitmq rabbitmq

