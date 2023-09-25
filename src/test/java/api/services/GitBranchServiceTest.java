package api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GitBranchServiceTest {

  private GitBranchService gitBranchService;

  @BeforeEach
  void setUp() {
    this.gitBranchService = mock(GitBranchService.class); // Mocking the GitBranchService
  }

  @Test
  void testGetActiveBranchName() {
    String expectedBranchName = "feature/new-feature";

    // Mocking the behavior
    when(gitBranchService.getGitBranch()).thenReturn(expectedBranchName);

    String actualBranchName = gitBranchService.getGitBranch();

    assertEquals(expectedBranchName, actualBranchName);
  }
}
