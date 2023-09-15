# Build the Docker image
build-image:
	docker build -t resume-app-image .

# Deploy the Docker container using the deployment script
deploy:
	./deploy.sh