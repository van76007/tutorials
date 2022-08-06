package com.baeldung.grpc.server;

import com.baeldung.grpc.client.GrpcClient;
import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcServer {
    private static final Logger logger = LoggerFactory.getLogger(GrpcServer.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
          .addService(new HelloServiceImpl()).build();

        logger.info("Starting server...");
        server.start();
        logger.info("Server started!");
        server.awaitTermination();
    }
}
