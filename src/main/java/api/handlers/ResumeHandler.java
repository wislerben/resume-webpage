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

  public ResumeHandler(final ResumeService resumeService) {
    this.resumeService = resumeService;
  }

  @Override
  public void handle(final HttpExchange exchange) throws IOException {
    setHeadersForCors(exchange);

    String resumeData = resumeService.getResumeData();

    // Respond to the client
    byte[] responseBytes = resumeData.getBytes();
    exchange.sendResponseHeaders(SUCCESS, responseBytes.length);
    try (OutputStream os = exchange.getResponseBody()) {
      os.write(responseBytes);
    }
  }

  private void setHeadersForCors(final HttpExchange exchange) {
    Headers headers = exchange.getResponseHeaders();
    if (headers != null) {
      headers.add("Access-Control-Allow-Origin", "*");
      headers.add("Access-Control-Allow-Methods", "GET, POST");
      headers.add("Access-Control-Allow-Headers", "Content-Type");
    }
  }
}
