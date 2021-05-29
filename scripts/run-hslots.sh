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
  echo "Hyderabad - [20] - [15 in 300]"
  curl -i $URL/hslots
  echo -e "\n"
  sleep 20
done
