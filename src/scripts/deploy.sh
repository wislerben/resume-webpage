#!/bin/bash

DOCKER_IMAGE_NAME="resume-app-image"

DOCKER_CONTAINER_NAME="resume-app-container"

PORT_MAPPING="8080:80"

APACHE2_CONFIG_FILE="/etc/apache2/sites-available/default-ssl.conf"

if [[ "$(docker images -q $DOCKER_IMAGE_NAME 2>/dev/null)" == "" ]]; then
  echo "Docker image $DOCKER_IMAGE_NAME not found. Build it first with 'make build-image'"
  exit 1
fi

# Check if the Docker container is already running
if [[ "$(docker ps -q -f name=$DOCKER_CONTAINER_NAME 2>/dev/null)" != "" ]]; then
  echo "Stopping and removing existing container: $DOCKER_CONTAINER_NAME"
  docker stop $DOCKER_CONTAINER_NAME
  docker rm $DOCKER_CONTAINER_NAME
fi

# Run the Docker container
echo "Starting a new container from image $DOCKER_IMAGE_NAME..."
docker run -d -p $PORT_MAPPING --name $DOCKER_CONTAINER_NAME $DOCKER_IMAGE_NAME

# Check if the container started successfully
if [[ "$(docker ps -q -f name=$DOCKER_CONTAINER_NAME 2>/dev/null)" != "" ]]; then
  echo "Container $DOCKER_CONTAINER_NAME is running and accessible at http://localhost:8080"

  # Apache2 Configuration
  echo "Configuring Apache2..."
  cat <<EOF >$APACHE2_CONFIG_FILE
  <VirtualHost *:80>
      ServerAdmin bwisler95@gmail.com
      ServerName resume-app.local
      # Make sure to change this so that the files aren't only available locally
      DocumentRoot /var/www/html/
      # ProxyPass for API requests
      ProxyPass /resume/ http://localhost:8080/resume/
      ProxyPassReverse /resume/ http://localhost:8080/resume/
      # Additional Apache2 settings here
  </VirtualHost>
EOF

  # Enable the Apache2 virtual host
  a2ensite default-ssl.conf

  # Check if we have sudo privileges to reload Apache2
  if [[ $(sudo -n -v 2>&1) == "" ]]; then
    # We have sudo privileges, so reload Apache2
    sudo systemctl reload apache2
  else
    echo "WARNING: Cannot reload Apache2 without sudo privileges. Please manually reload Apache2."
  fi

  echo "Apache2 configuration completed."
else
  echo "Failed to start the container. Check the Docker logs for details."
fi
