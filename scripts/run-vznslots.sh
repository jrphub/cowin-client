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
  echo "Vizianagaram - [40s] - [7 in 300]"
  curl -i $URL/vznslots
  echo -e "\n"
  sleep 40
done
