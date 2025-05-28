#!/bin/bash

echo "List of applications to be uninstalled"
helm list

echo "Executing command for uninstalling: 'helm uninstall grafana keycloak rabbitmq kafka loki tempo prometheus jenkins'"
helm uninstall grafana keycloak rabbitmq kafka loki tempo prometheus jenkins

echo "List of remaining applications to be uninstalled"
helm list

echo "Deleting all persistance Volume Claims: 'kubectl delete pvc --all'"
kubectl delete pvc --all

echo "Deleting all Persistance Volumes: 'kubectl delete pv --all'"
kubectl delete pv --all

