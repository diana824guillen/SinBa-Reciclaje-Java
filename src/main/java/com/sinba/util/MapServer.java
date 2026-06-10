package com.sinba.util;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class MapServer {

    private static HttpServer server;

    public static int start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(0), 0);
        int port = server.getAddress().getPort();

        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String requestPath = exchange.getRequestURI().getPath();
                if ("/".equals(requestPath) || "/mapa.html".equals(requestPath)) {
                    // Leer el HTML desde el classpath
                    String html = readResource("mapa.html");
                    exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                    byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
                    exchange.sendResponseHeaders(200, bytes.length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(bytes);
                    os.close();
                } else {
                    String response = "404 Not Found";
                    exchange.sendResponseHeaders(404, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            }
        });

        server.setExecutor(null);
        server.start();
        return port;
    }

    public static void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    private static String readResource(String resourcePath) throws IOException {
        InputStream input = MapServer.class.getResourceAsStream("/" + resourcePath);
        if (input == null) {
            throw new FileNotFoundException("Recurso no encontrado: " + resourcePath);
        }
        return new String(input.readAllBytes(), StandardCharsets.UTF_8);
    }
}