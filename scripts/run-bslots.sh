#!/bin/bash
MODE=$1
if [[ $MODE == "local" ]]
then
  URL=localhost:9000
else
  URL=https://cowin-client.herokuapp.com
fi

while true
do
  echo "Bengaluru - [1m] - [3 in 300s]"
  curl -i $URL/bslots
  echo -e "\n"
  sleep 1m
done
