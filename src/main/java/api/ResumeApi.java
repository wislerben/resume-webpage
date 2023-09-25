package api;

import api.handlers.CoverageFileHandler;
import api.handlers.GitBranchHandler;
import api.handlers.HealthCheckHandler;
import api.handlers.ResumeHandler;
import api.services.DefaultGitBranchService;
import api.services.DefaultResumeService;
import com.sun.net.httpserver.HttpServer;/**/
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ResumeApi {
  private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  // Private constructor to prevent instantiation
  private ResumeApi() {
    throw new AssertionError("Utility class should not be instantiated");
  }

  public static void main(final String[] args) {
    int port = 3000;

    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
      System.out.println("API server is running on port " + port);
      System.out.println("Resume webpage: http://localhost:8080");
      server.createContext("/api/resume", new ResumeHandler(new DefaultResumeService()));
      server.createContext("/api/git-branch", new GitBranchHandler(new DefaultGitBranchService()));
      server.createContext(
          "/jacoco-resources", new CoverageFileHandler("../site/jacoco/jacoco-resources"));
      server.createContext("/health", new HealthCheckHandler());
      server.createContext("/", new CoverageFileHandler("../site/jacoco"));
      server.setExecutor(null);

      server.start();
    } catch (java.io.IOException e) {
      LOGGER.log(Level.SEVERE, "I/O exception thrown", e);
    }
  }
}
