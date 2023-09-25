package api.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class DefaultGitBranchService implements GitBranchService {
  @Override
  public String getGitBranch() {
    try {
      List<String> commands = Arrays.asList("git", "rev-parse", "--abbrev-ref", "HEAD");
      ProcessBuilder processBuilder = new ProcessBuilder(commands);
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String branch = reader.readLine();
      process.waitFor();
      return branch;
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      return "Error";
    }
  }
}
