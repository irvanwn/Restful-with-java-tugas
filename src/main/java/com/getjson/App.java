package com.getjson;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        URI baseUri = URI.create("http://127.0.0.1:8080/");
        ResourceConfig config = new ResourceConfig(controllerBarang.class, controllerRegister.class,
                controllerTransaksi.class);

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);

        try {
            server.start();
            System.out.println("server mlaku" + baseUri);

        } catch (Exception e) {
            System.err.println("Server Error" + e.getMessage());
        }

    }
}
