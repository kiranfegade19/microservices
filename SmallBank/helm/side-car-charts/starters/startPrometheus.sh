#!/bin/bash

echo "###################################################################"
echo "Building and starting Prometheus"
cd ../kube-prometheus
echo "Inside folder $PWD, building kafka helm chart"
helm dependencies build

cd ../
echo "Inside folder $PWD, starting prometheus using helm"
helm install prometheus kube-prometheus

