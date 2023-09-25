package api.handlers;

import api.services.ResumeService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

import static api.HttpConstants.SUCCESS;

public class ResumeHandler implements HttpHandler {

    private final ResumeService resumeService;

    public ResumeHandler(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        setHeadersForCORS(exchange);

        String resumeData = resumeService.getResumeData();

        // Respond to the client
        byte[] responseBytes = resumeData.getBytes();
        exchange.sendResponseHeaders(SUCCESS, responseBytes.length);
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