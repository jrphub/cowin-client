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
  echo "Khurda18 - [15s] - [20 in 300]"
  curl -i $URL/kslots18
  echo -e "\n"
  sleep 15
done
