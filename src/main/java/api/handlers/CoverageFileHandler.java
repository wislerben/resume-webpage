package api.handlers;

import static api.HttpConstants.*;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class CoverageFileHandler implements HttpHandler {
  private final String baseDirectory;

  public CoverageFileHandler(final String baseDirectory) {
    this.baseDirectory = baseDirectory;
  }

  @Override
  public void handle(final HttpExchange httpExchange) {
    try {
      Headers headers = httpExchange.getResponseHeaders();
      headers.add("Access-Control-Allow-Origin", "*");
      headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
      headers.add(
          "Access-Control-Allow-Headers",
          "Origin, X-Requested-With, Content-Type, Accept, Authorization");
      String cwd = System.getProperty("user.dir");
      System.out.println("Current working directory using System: " + cwd);
      String requestedPath;

      try {
        requestedPath = httpExchange.getRequestURI().getPath();
        HttpContext context = httpExchange.getHttpContext();
      } catch (NullPointerException e) {
        System.err.println("Null pointer exception: unable to proceed.");
        sendError(httpExchange, INTERNAL_SERVER_ERROR, "Internal Server Error");
        return;
      }
      String contextPath = httpExchange.getHttpContext().getPath();

      String relativePath = requestedPath.substring(contextPath.length());

      // If the request is for jacoco-resources, adjust the path
      if (relativePath.startsWith("/jacoco-resources")) {
        relativePath = relativePath.replaceFirst("/jacoco-resources", "");
      }

      // If the relative path is empty or "/", serve index.html
      if (relativePath.isEmpty() || relativePath.equals("/")) {
        relativePath = "index.html";
      }

      File file = new File(baseDirectory, relativePath).getCanonicalFile();
      if (!file.isFile() || !file.canRead()) {
        System.out.println(
            "Failed to serve file (File not found or not accessible): " + file.getAbsolutePath());
        send404(httpExchange);
        return;
      }
      serveFile(httpExchange, file);

    } catch (Exception e) {
      System.out.println("Exception occurred while processing the request: " + e.getMessage());
      e.printStackTrace(); // print the stack trace for more detailed debugging
      try {
        sendError(httpExchange, INTERNAL_SERVER_ERROR, "Internal Server Error");
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    }
  }

  private void send404(final HttpExchange httpExchange) throws IOException {
    sendError(httpExchange, NOT_FOUND, "404 (Not Found)\n");
  }

  private void serveFile(final HttpExchange httpExchange, final File file) throws IOException {
    Headers headers = httpExchange.getResponseHeaders();
    String contentType = guessContentTypeFromName(file.getName());
    headers.add("Content-Type", contentType);

    byte[] content = Files.readAllBytes(file.toPath());

    httpExchange.sendResponseHeaders(SUCCESS, content.length);
    try (OutputStream os = httpExchange.getResponseBody()) {
      os.write(content);
    }
  }

  private String guessContentTypeFromName(final String name) {
    if (name.endsWith(".css")) {
      return "text/css";
    } else if (name.endsWith(".gif")) {
      return "image/gif";
    } else if (name.endsWith(".js")) {
      return "application/javascript";
    }
    return "text/html";
  }

  private void sendError(
      final HttpExchange httpExchange, final int statusCode, final String responseMessage)
      throws IOException {
    httpExchange.sendResponseHeaders(statusCode, responseMessage.length());
    try (OutputStream os = httpExchange.getResponseBody()) {
      os.write(responseMessage.getBytes(StandardCharsets.UTF_8));
    }
  }
}
