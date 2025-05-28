#!/bin/bash

echo "###################################################################"
echo "Building and starting Kafka"
cd ../kafka
echo "Inside folder $PWD, building kafka helm chart"
helm dependencies build

cd ../
echo "Inside folder $PWD, starting kafka using helm"
helm install kafka kafka

