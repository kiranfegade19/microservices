#!/bin/bash

echo "###################################################################"
echo "Building and starting Loki"
cd ../grafana-loki
echo "Inside folder $PWD, building loki helm chart"
helm dependencies build

cd ../
echo "Inside folder $PWD, starting loki using helm"
helm install loki grafana-loki

