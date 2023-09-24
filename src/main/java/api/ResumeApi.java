package api;

import java.io.*;
import java.util.logging.*;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;

import api.services.DefaultGitBranchService;
import api.services.DefaultResumeService;
import api.handlers.GitBranchHandler;
import api.handlers.ResumeHandler;

public class ResumeApi {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        int port = 3000;

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("Server is running on port " + port);

            server.createContext("/api/resume", new ResumeHandler(new DefaultResumeService()));
            server.createContext("/api/git-branch", new GitBranchHandler(new DefaultGitBranchService()));

            server.start();
        } catch (java.io.IOException e) {
            LOGGER.log(Level.SEVERE, "I/O exception thrown", e);
        }
    }
}
