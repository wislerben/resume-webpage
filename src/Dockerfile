FROM --platform=linux/amd64 ubuntu:20.04

# Install curl, Apache2, Node.js, and other necessary packages
RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y curl apache2 && \
    curl -sL https://deb.nodesource.com/setup_14.x | bash - && \
    apt-get install -y nodejs && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Create a directory for your project
WORKDIR /var/www/html

# Copy package.json and package-lock.json to the container
COPY /main/java/api/package*.json ./

# Install Node.js dependencies
RUN npm install --production

# Clean npm cache
RUN npm cache clean --force

# Copy the rest of your project files to the container
COPY ../main/java .

# Install express.js
RUN npm install express --save

# Install json-server
RUN npm install -g json-server

RUN npm install fs cors --save


# Expose port 80 for Apache
EXPOSE 80

# Start Apache2 and JSON Server in the foreground
CMD ["bash", "-c", "apachectl -DFOREGROUND & json-ApiServer --watch db.json & wait"]
#CMD ["bash", "-c", "apachectl -DFOREGROUND & json-ApiServer --watch db.json"]
