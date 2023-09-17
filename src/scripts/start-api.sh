#!/bin/bash

# Define the name of your Node.js script and the process ID file
NODE_SCRIPT="./main/java/api/app.js"
PID_FILE="./main/java/api/server.pid"

# Check if the process ID file exists
if [ -f "$PID_FILE" ]; then
  # Get the process ID from the file
  PID=$(cat "$PID_FILE")

  # Check if the process with the stored PID is still running
  if ps -p $PID > /dev/null; then
    # The server is running, so let's stop it gracefully
    echo "Stopping the server with PID $PID"
    kill $PID

    # Wait for the server to stop (adjust the sleep duration as needed)
    sleep 5
  fi
fi

# Start the server
echo "Starting the server..."
node "$NODE_SCRIPT" &

# Get the new process ID and save it to the PID file
echo $! > "$PID_FILE"

echo "Server started with PID $!"

# Navigate to your Node.js application directory (if not already there)
#cd ./api

# Start your Node.js application
#node app.js
# &
#PID=$!
#
## Write the PID to a PID file
#echo $PID > ./api/server.pid



#!/bin/bash
