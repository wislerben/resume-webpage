#!/bin/bash
APACHE_PORT=8080
RESUME_PORT=3000
ports=($APACHE_PORT $RESUME_PORT)

for port in "${ports[@]}"; do
    lsof_pid=$(lsof -t -i :"$port")
    # shellcheck disable=SC2236
    if [ ! -z "$lsof_pid" ]; then
        echo "Port $port is in use by PID $lsof_pid. Killing..."
        kill -9 "$lsof_pid"
    fi
done
