package api.handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static api.HttpConstants.INTERNAL_SERVER_ERROR;
import static api.HttpConstants.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;

public class CoverageFileHandlerTest {

  private CoverageFileHandler coverageFileHandler;
  private HttpExchange mockHttpExchange;
  private Headers mockHeaders;
  private OutputStream mockOutputStream;

  @BeforeEach
  public void setUp() throws IOException {
    String baseDirectory = "src/test/";

    mockHttpExchange = mock(HttpExchange.class);
    HttpContext mockHttpContext = mock(HttpContext.class);

    // Added the following line to mock the getPath() method behavior
    when(mockHttpContext.getPath()).thenReturn("/some/path");

    when(mockHttpExchange.getHttpContext()).thenReturn(mockHttpContext);

    mockHeaders = new Headers();
    mockOutputStream = mock(OutputStream.class);

    when(mockHttpExchange.getResponseHeaders()).thenReturn(mockHeaders);
    when(mockHttpExchange.getResponseBody()).thenReturn(mockOutputStream);

    coverageFileHandler = new CoverageFileHandler(baseDirectory);
  }

  @Test
  public void testHandleWithValidFile() throws IOException, URISyntaxException {
    String cwd = System.getProperty("user.dir");
    System.out.println("Current working directory using System: " + cwd);
    when(mockHttpExchange.getRequestURI()).thenReturn(new URI("/src/test/resources/valid.html"));

    coverageFileHandler.handle(mockHttpExchange);

    // Capture the arguments passed to sendResponseHeaders
    ArgumentCaptor<Integer> statusCodeCaptor = ArgumentCaptor.forClass(Integer.class);
    ArgumentCaptor<Long> contentLengthCaptor = ArgumentCaptor.forClass(Long.class);
    verify(mockHttpExchange, times(1))
        .sendResponseHeaders(statusCodeCaptor.capture(), contentLengthCaptor.capture());

    assertEquals(SUCCESS, statusCodeCaptor.getValue().intValue());
    assertTrue(contentLengthCaptor.getValue() > 0L);
  }

  @Test
  public void testHandleWithInvalidFile() throws IOException, URISyntaxException {
    // Mock Headers
    Headers mockHeaders = mock(Headers.class);
    when(mockHttpExchange.getResponseHeaders()).thenReturn(mockHeaders);

    // Mock other methods
    HttpContext mockHttpContext = mock(HttpContext.class);
    when(mockHttpExchange.getHttpContext()).thenReturn(mockHttpContext);
    when(mockHttpContext.getPath()).thenReturn("/somePath");

    // Execute the method
    coverageFileHandler.handle(mockHttpExchange);

    // Validate
    verify(mockHttpExchange, times(1))
        .sendResponseHeaders(INTERNAL_SERVER_ERROR, "Internal Server Error".length());
  }
}
