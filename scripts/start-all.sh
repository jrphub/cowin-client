#!/bin/bash
echo "Starting all Process for Cowin-client"
cd /home/ubuntu/cowin-client/scripts
./run-bslots.sh > /dev/null 2>&1 &
sleep 2s
./run-cslots18.sh > /dev/null 2>&1 &
sleep 2s
./run-cslots45.sh > /dev/null 2>&1 &
sleep 2s
./run-hslots.sh > /dev/null 2>&1 &
sleep 2s
./run-kslots18.sh > /dev/null 2>&1 &
sleep 2s
./run-kslots45.sh > /dev/null 2>&1 &
sleep 2s
./run-mslots.sh > /dev/null 2>&1 &
sleep 2s
./run-klhslots.sh > /dev/null 2>&1 &
sleep 2s
./run-vznslots.sh > /dev/null 2>&1 &

sleep 2
echo "All Process Started"