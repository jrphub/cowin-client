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
  echo "Kalahandi - [1m] - [3 in 300]"
  curl -i $URL/klhslots
  echo -e "\n"
  sleep 1m
done
