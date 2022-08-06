package com.baeldung.grpc.server;

import com.baeldung.grpc.client.GrpcClient;
import java.io.IOException;
import java.util.concurrent.Executors;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.util.Objects;
import io.grpc.netty.NettyServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcServer {
    private static final Logger logger = LoggerFactory.getLogger(GrpcServer.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        /*
        Server server = ServerBuilder.forPort(8080)
          .addService(new HelloServiceImpl()).build();
         */
        Server server = configureExecutor()
                .c
                .addService(new HelloServiceImpl()).build();

        logger.info("Starting server...");
        server.start();
        logger.info("Server started!");
        server.awaitTermination();
    }

    private static io.grpc.netty.NettyServerBuilder configureExecutor() {
        // Local test
        NettyServerBuilder sb = io.grpc.netty.NettyServerBuilder.forPort(8080);

        String threads = System.getenv("JVM_EXECUTOR_THREADS");
        int i_threads = Runtime.getRuntime().availableProcessors();
        if (threads != null && !threads.isEmpty()) {
            i_threads = Integer.parseInt(threads);
        }

        // In principle, number of threads should be equal to number of CPUs but let try 256
        i_threads = i_threads * 16;

        String value = System.getenv().getOrDefault("JVM_EXECUTOR_TYPE", "fixed");
        System.out.println("Number of threads " + i_threads + " and executor style=" + value);

        if (Objects.equals(value, "direct")) {
            sb = sb.directExecutor();
        } else if (Objects.equals(value, "single")) {
            sb = sb.executor(Executors.newSingleThreadExecutor());
        } else if (Objects.equals(value, "fixed")) {
            sb = sb.executor(Executors.newFixedThreadPool(i_threads));
        } else if (Objects.equals(value, "workStealing")) {
            sb = sb.executor(Executors.newWorkStealingPool(i_threads));
        } else if (Objects.equals(value, "cached")) {
            sb = sb.executor(Executors.newCachedThreadPool());
        }
        return sb;
    }
}
