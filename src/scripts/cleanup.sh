#!/bin/bash

CONTAINER_NAME="resume-app-container"
# Stop and remove the Docker container if it exists
if docker ps -a --format '{{.Names}}' | grep -Eq "^$CONTAINER_NAME$"; then
  echo "Stopping and removing Docker container: $CONTAINER_NAME"
  docker stop $CONTAINER_NAME
  docker rm $CONTAINER_NAME
else
  echo "Docker container $CONTAINER_NAME does not exist."
fi

# Stop Apache if it's running
if systemctl is-active --quiet apache2; then
  echo "Stopping Apache..."
  sudo systemctl stop apache2
else
  echo "Apache is not running."
fi

echo "Cleanup completed."

sleep 1
exit
