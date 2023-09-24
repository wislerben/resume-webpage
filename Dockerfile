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
RUN mvn clean package

# Copy the generated artifact (assuming it's a .war or .jar) to the appropriate location
# Modify this based on your application's needs

# Expose port 80 for Apache
EXPOSE 80

# Start Apache2 and JSON Server in the foreground
CMD ["bash", "-c", "apachectl -DFOREGROUND & json-ApiServer --watch db.json & wait"]
