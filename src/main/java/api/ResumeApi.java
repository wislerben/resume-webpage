package api;

import java.io.*;
import java.util.logging.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;

public class ResumeApi {
    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static void setHeadersForCORS(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
    }
    public static void main(String[] args) {
        int port = 3000;
        String currentWorkingDirectory = System.getProperty("user.dir");
        System.out.println("!Current working directory: " + currentWorkingDirectory);

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("Server is running on port " + port);
            server.createContext("/api/resume", new ResumeHandler());
            server.createContext("/api/git-branch", new GitBranchHandler());
            server.start();
        } catch (java.io.IOException e) {
            LOGGER.log(Level.SEVERE, "I/O exception thrown", e);
        }
    }


    static class ResumeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setHeadersForCORS(exchange);
            String filePath = "../../src/main/java/api/db.json";
            StringBuilder jsonData = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    jsonData.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                exchange.sendResponseHeaders(500, 0);
                return;
            }

            byte[] responseBytes = jsonData.toString().getBytes();
            exchange.sendResponseHeaders(200, responseBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        }
    }

    static class GitBranchHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setHeadersForCORS(exchange);
            String gitBranch = getGitBranch();

            byte[] responseBytes = gitBranch.getBytes();
            exchange.sendResponseHeaders(200, responseBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        }

        private String getGitBranch() {
            try {
                Process process = Runtime.getRuntime().exec("git rev-parse --abbrev-ref HEAD");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String branch = reader.readLine();
                process.waitFor();
                return "{\"branchName\":\"" + branch + "\"}";
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return "{\"branchName\":\"Error\"}";
            }
        }
    }
}