package api;

public final class HttpConstants {
  // Private constructor to prevent instantiation
  private HttpConstants() {
    throw new AssertionError("Utility class should not be instantiated");
  }

  public static final int INTERNAL_SERVER_ERROR = 500;
  public static final int NOT_FOUND = 404;
  public static final int SUCCESS = 200;
}
