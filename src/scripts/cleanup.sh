#!/bin/bash

# Define the container name
CONTAINER_NAME="resume-app-container"

# Stop and remove the Docker container if it exists
if docker ps -a --format '{{.Names}}' | grep -Eq "^$CONTAINER_NAME$"; then
  echo "Stopping and removing Docker container: $CONTAINER_NAME"
  docker stop $CONTAINER_NAME
  docker rm $CONTAINER_NAME
else
  echo "Docker container $CONTAINER_NAME does not exist."
fi

# Stop Apache if it's running (assuming it's named "apache2")
if systemctl is-active --quiet apache2; then
  echo "Stopping Apache..."
  sudo systemctl stop apache2
else
  echo "Apache is not running."
fi

# Stop Node.js if it's running (assuming it's running on port 3000)
if lsof -Pi :3000 -sTCP:LISTEN -t >/dev/null ; then
  echo "Stopping Node.js..."
  # You may need to customize this command depending on how your Node.js is started
  # Replace "node" with the actual Node.js process name
  kill $(pgrep node)
else
  echo "Node.js is not running on port 3000."
fi

# Optionally, you can add more cleanup steps here

echo "Cleanup completed."

# Sleep for a few seconds (e.g., 5 seconds) before closing the terminal
sleep 1

# Close the terminal window
exit