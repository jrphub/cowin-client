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
  echo "Mumbai - [15s] - [20 in 300]"
  curl -i $URL/mslots
  echo -e "\n"
  sleep 15
done
