#!/bin/bash

echo "###################################################################"
echo "Building and starting Jenkins"
cd ../jenkins
echo "Inside folder $PWD, building jenkins helm chart"
helm dependencies build

cd ../
echo "Inside folder $PWD, starting jenkins using helm"
helm install jenkins jenkins

