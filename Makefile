# Build the Docker image
 build-image:
	docker build --platform=linux/amd64 -t resume-app-image .

 deploy:
	./src/scripts/deploy.sh
#
 run-api-server:
	./src/scripts/start_api.sh

build-and-run: build-image deploy run-api-server

cleanup:
	./src/scripts/free_ports.sh
	./src/scripts/cleanup.sh