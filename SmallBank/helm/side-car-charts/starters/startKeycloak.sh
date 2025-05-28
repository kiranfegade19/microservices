#!/bin/bash

echo "###################################################################"
echo "Building and starting Keycloak"
cd ../keycloak
echo "Inside folder $PWD, building Keycloak helm chart"
helm dependencies build

cd ../
echo "Inside folder $PWD, starting Keycloak using helm"
helm install keycloak keycloak

