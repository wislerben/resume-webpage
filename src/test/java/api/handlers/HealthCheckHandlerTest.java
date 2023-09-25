package api.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static api.HttpConstants.SUCCESS;

class HealthCheckHandlerTest {

  private HealthCheckHandler healthCheckHandler;
  private HttpExchange mockHttpExchange;
  private Headers headers;
  private OutputStream os;

  @BeforeEach
  public void setup() {
    healthCheckHandler = new HealthCheckHandler();
    mockHttpExchange = mock(HttpExchange.class);
    headers = new Headers();
    os = new ByteArrayOutputStream();

    when(mockHttpExchange.getResponseHeaders()).thenReturn(headers);
    when(mockHttpExchange.getResponseBody()).thenReturn(os);
  }

  @Test
  public void testHandlerRespondsWithSuccessStatusCode() throws IOException {
    healthCheckHandler.handle(mockHttpExchange);

    verify(mockHttpExchange, times(1)).sendResponseHeaders(SUCCESS, "OK".length());
  }

  @Test
  public void testHandlerRespondsWithOkMessage() throws IOException {
    healthCheckHandler.handle(mockHttpExchange);

    String response = os.toString();
    assert response.contains("OK");
  }
}
