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
  echo "Test - [3s] - [100 in 300]"
  curl -i $URL/test2
  echo -e "\n"
  sleep 15
done
