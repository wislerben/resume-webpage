# Use a specific platform
FROM --platform=linux/amd64 ubuntu:20.04

# Update and install necessary packages
RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y apache2 maven openjdk-11-jdk && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Create a directory for your project
WORKDIR /app

# Copy the Java project and build with Maven
COPY ./src/main/java /var/www/html
COPY ./pom.xml .

# Expose port 80 for Apache
EXPOSE 80

# Expose port 3000 for API
EXPOSE 3000

# Start Apache2 and JSON Server in the foreground
CMD ["bash", "-c", "apachectl -DFOREGROUND & json-ApiServer --watch db.json & wait"]
