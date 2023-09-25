# Resume Web Application

This project is a Resume Web Application showcasing resume data in JSON format, along with additional utilities such as current branch name retrieval, health checks, and test coverage data.

## Prerequisites

- Docker
- Java (for local development)
- Make

## Quick Start

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/wislerben/resume-webpage.git
   cd resume-webpage

2. **Start docker desktop**

3. **Build docker image, run tests, install dependencies, run servers:**
   ```bash
   make build-and-run

4. **Navigate to [https://localhost:8080](https://localhost:8080)**

5. **Cleanup server processes and delete docker image**
    ```bash
   make cleanup
## Other Commands
1. **Remove old files in target directory and package project**
    ```bash
   mvn-pack
2. **Build docker image**
    ```bash
   make build-image
3. **Start apache server on port 8080**
    ```bash
   make deploy
4. **Start API server on port 3000**
    ```bash
   make run-api-server
5. **Cleanup processes**
    ```bash
   make cleanup
   
## TEST Design Pattern Documentation Here:
---------->&emsp; [TEST.md](./TEST.md)