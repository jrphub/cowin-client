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
  echo "Khurda45 - [10m] - [1 in 300]"
  curl -i $URL/kslots45
  echo -e "\n"
  sleep 10m
done
