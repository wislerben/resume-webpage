#!/bin/bash

mvn compile

MAIN_CLASS="api.ResumeApi"

CLASSPATH="target/classes"

PID_FILE="../../src/main/java/api/server.pid"

# Check if the process ID file exists
if [ -f "$PID_FILE" ]; then
  # Get the process ID from the file
  PID=$(cat "$PID_FILE")

  # Check if the process with the stored PID is still running
  if ps -p "$PID" >/dev/null; then
    # The server is running, so stop it
    echo "Stopping the server with PID $PID"
    kill "$PID"

    # Wait for the server to stop
    sleep 5
  fi
fi

# Change to the directory containing the compiled classes
cd "$CLASSPATH"

# Start the Java application
echo "Starting the Java application..."
java "$MAIN_CLASS" &

# Get the new process ID and save it to the PID file
echo $! >"$PID_FILE"

echo "Java application started with PID $!"
