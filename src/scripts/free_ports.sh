#!/bin/bash

ports=("8080" "3000")

for port in "${ports[@]}"; do
    lsof_pid=$(lsof -t -i :"$port")
    # shellcheck disable=SC2236
    if [ ! -z "$lsof_pid" ]; then
        echo "Port $port is in use by PID $lsof_pid. Killing..."
        kill -9 "$lsof_pid"
    fi
done
