#!/bin/bash


if [ $# -eq 0 ]; then
  echo "ERROR: No envoronment parameter provided. Execute script with env parameter like below"
  echo "                ./startSmallbank.sh dev-env"
  echo "                           OR"
  echo "                ./startSmallbank qa-env"
  echo "                           OR"
  echo "                ./startSmallbank.sh prod-env"
  exit 1
else
  echo "Parameter provided: $1"
fi

environment="$1"

echo "Starting Smallbank in $environment. Check logs at ./logs/smallbank-$environment.logs"

echo "###################################" >> ./logs/smallbank-$environment.logs
date >> ./logs/smallbank-$environment.logs

cd $environment

helm dependencies build >> ./logs/smallbank-$environment.logs

cd ../

helm install smallbank-$environment $environment >> ./logs/smallbank-$environment.logs

echo
echo

helm list
helm list >> ./logs/smallbank-$environment.logs

