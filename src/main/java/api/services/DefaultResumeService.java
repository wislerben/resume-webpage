package api.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DefaultResumeService implements ResumeService {
    @Override
    public String getResumeData() {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("db.json")) {
            if (stream == null) {
                throw new IOException("Cannot find db.json in classpath");
            }
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read db.json from classpath", e);
        }
    }
}
