# Build the Docker image
build-image:
	docker build --platform=linux/amd64 -t resume-app-image .

deploy:
	./scripts/deploy.sh

run-api-server:
	./scripts/start-api.sh

cleanup:
	./scripts/cleanup.sh