package com.baeldung.grpc.server;

import com.baeldung.grpc.HelloRequest;
import com.baeldung.grpc.HelloResponse;
import com.baeldung.grpc.HelloServiceGrpc.HelloServiceImplBase;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServiceImpl extends HelloServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class.getName());

    @Override
    public void hello(
      HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        logger.info("Request received from client:\n" + request);

        String greeting = new StringBuilder().append("Hello, ")
            .append(request.getFirstName())
            .append(" ")
            .append(request.getLastName())
            .toString();

        HelloResponse response = HelloResponse.newBuilder()
            .setGreeting(greeting)
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
