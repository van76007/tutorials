package com.baeldung.grpc.client;

import static java.lang.Thread.currentThread;

import com.baeldung.grpc.HelloRequest;
import com.baeldung.grpc.HelloResponse;
import com.baeldung.grpc.HelloServiceGrpc;

import com.baeldung.grpc.streaming.StockClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcClient {
    private static final Logger logger = LoggerFactory.getLogger(GrpcClient.class.getName());

    public static void main(String[] args) throws InterruptedException {
        // version1();
        version2();
    }

    static void version1() throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
            .usePlaintext()
            .build();

        HelloServiceGrpc.HelloServiceBlockingStub stub
            = HelloServiceGrpc.newBlockingStub(channel);

        for (int i = 0; i < 10; i++) {
            HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                .setFirstName("Baeldung")
                .setLastName("gRPC")
                .build());
            logger.info("Response received from server:\n" + helloResponse);
            Thread.sleep(100);
        }

        channel.shutdown();
    }

    static void version2() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 10; i++) {
            threadPool.submit(
                new Runnable() {
                    public void run() {
                        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                            .usePlaintext()
                            .build();
                        HelloServiceGrpc.HelloServiceBlockingStub stub
                            = HelloServiceGrpc.newBlockingStub(channel);
                        HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                            .setFirstName("Baeldung")
                            .setLastName("gRPC")
                            .build());
                        logger.info("Response received from server:\n" + helloResponse);
                        channel.shutdown();
                    }
                }
            );
        }
    }

    static void version3() throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
            .usePlaintext()
            .build();
        HelloServiceGrpc.HelloServiceStub stub
            = HelloServiceGrpc.newStub(channel);

        HelloRequest r = HelloRequest.newBuilder()
            .setFirstName("Baeldung")
            .setLastName(currentThread().getName())
            .build();

        StreamObserver<HelloResponse> o = new StreamObserver<HelloResponse>() {
            @Override
            public void onNext(HelloResponse helloResponse) {
                logger.info("Response received from server:\n" + helloResponse);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        };

        stub.hello(r, o);
        channel.shutdown();
    }

    static void version4() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        /*
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
            .usePlaintext()
            .build();
        HelloServiceGrpc.HelloServiceStub stub
            = HelloServiceGrpc.newStub(channel);
        */

        for (int i = 0; i < 10; i++) {
            threadPool.submit(
                new Runnable() {
                    public void run() {
                        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                            .usePlaintext()
                            .build();
                        HelloServiceGrpc.HelloServiceStub stub
                            = HelloServiceGrpc.newStub(channel);

                        HelloRequest r = HelloRequest.newBuilder()
                            .setFirstName("Baeldung")
                            .setLastName(currentThread().getName())
                            .build();

                        StreamObserver<HelloResponse> o = new StreamObserver<HelloResponse>() {
                            @Override
                            public void onNext(HelloResponse helloResponse) {
                                logger.info("Response received from server:\n" + helloResponse);
                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public void onCompleted() {

                            }
                        };

                        stub.hello(r, o);
                        channel.shutdown();
                    }
                }
            );
        }
    }
}
