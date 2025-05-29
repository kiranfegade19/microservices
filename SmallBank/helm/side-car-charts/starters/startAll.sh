#!/bin/bash

./startPrometheus.sh > ../logs/prometheus.logs

./startTempo.sh > ../logs/tempo.logs

./startLoki.sh > ../logs/loki.logs

./startKafka.sh > ../logs/kafka.logs

./startRabbitMQ.sh > ../logs/rabbitmq.logs

./startKeycloak.sh > ../logs/keycloak.logs

echo "Start waiting"

sleep 30s

echo "End waiting"

./startGrafana.sh > ../logs/grafana.logs



