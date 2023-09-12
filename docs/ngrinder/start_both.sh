#!/bin/bash

# Start controller in the background
java -jar ngrinder-controller-3.5.8.war --port 8000 &

# Give controller some time to start
sleep 20

# Start agent
./ngrinder-agent/run_agent.sh
