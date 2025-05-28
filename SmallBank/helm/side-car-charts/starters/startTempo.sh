#!/bin/bash

echo "###################################################################"
echo "Building and starting Tempo"
cd ../grafana-tempo
echo "Inside folder $PWD, building tempo helm chart"
helm dependencies build

cd ../
echo "Inside folder $PWD, starting tempo using helm"
helm install tempo grafana-tempo

