package api.handlers;

import api.services.GitBranchService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class GitBranchHandler implements HttpHandler {

    private final GitBranchService gitBranchService;

    public GitBranchHandler(GitBranchService gitBranchService) {
        this.gitBranchService = gitBranchService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        setHeadersForCORS(exchange);

        String gitBranch = gitBranchService.getGitBranch();
        String jsonResponse = "{\"branchName\":\"" + gitBranch + "\"}";


        // Respond to the client
        byte[] responseBytes = jsonResponse.getBytes();
        exchange.sendResponseHeaders(200, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }

    private void setHeadersForCORS(HttpExchange exchange) {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
    }
}
