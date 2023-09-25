package api.handlers;

import api.services.ResumeService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;

import static api.HttpConstants.SUCCESS;
import static org.mockito.Mockito.*;

public class ResumeHandlerTest {
  private ResumeHandler resumeHandler;
  private ResumeService mockResumeService;
  private HttpExchange mockHttpExchange;
  private OutputStream mockOutputStream;

  @BeforeEach
  public void setUp() {
    mockResumeService = mock(ResumeService.class);
    mockHttpExchange = mock(HttpExchange.class);
    mockOutputStream = mock(OutputStream.class);

    when(mockResumeService.getResumeData()).thenReturn("Test Resume Data");
    when(mockHttpExchange.getResponseBody()).thenReturn(mockOutputStream);

    resumeHandler = new ResumeHandler(mockResumeService);
  }

  @Test
  public void testHandle() throws IOException {
    resumeHandler.handle(mockHttpExchange);

    verify(mockHttpExchange, times(1)).sendResponseHeaders(SUCCESS, "Test Resume Data".length());
    verify(mockOutputStream, times(1)).write("Test Resume Data".getBytes());
  }
}
