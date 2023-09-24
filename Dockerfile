FROM --platform=linux/amd64 ubuntu:20.04

RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y curl apache2 && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Create a directory for your project
WORKDIR /var/www/html

# Copy the rest of your project files to the container
COPY ./src/main/java .

# Expose port 80 for Apache
EXPOSE 80

# Start Apache2 and JSON Server in the foreground
CMD ["bash", "-c", "apachectl -DFOREGROUND & json-ApiServer --watch db.json & wait"]
