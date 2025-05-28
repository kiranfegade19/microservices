#!/bin/bash

echo "###################################################################"
echo "Building and starting Grafana"
cd ../grafana
echo "Inside folder $PWD, building Grafana helm chart"
helm dependencies build

cd ../
echo "Inside folder $PWD, starting grafana using helm"
helm install grafana grafana

