package api.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DefaultGitBranchService implements GitBranchService {
    @Override
    public String getGitBranch() {
        try {
            Process process = Runtime.getRuntime().exec("git rev-parse --abbrev-ref HEAD");
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
