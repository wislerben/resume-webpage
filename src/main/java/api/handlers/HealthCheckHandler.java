package api.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static api.HttpConstants.SUCCESS;

public class HealthCheckHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
        headers.add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        String responseMessage = "OK";
        httpExchange.sendResponseHeaders(SUCCESS, responseMessage.length());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(responseMessage.getBytes(StandardCharsets.UTF_8));
        }
    }
}
