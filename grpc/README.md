## gRPC

This module contains articles about gRPC

### Relevant Articles:

- [Introduction to gRPC](https://www.baeldung.com/grpc-introduction)
- [Streaming with gRPC in Java](https://www.baeldung.com/java-grpc-streaming)
- [Error Handling in gRPC](https://www.baeldung.com/grpcs-error-handling)

### Wireshark tips:
1. Preferences -> Protobuf -> Load all proto files under src/main/proto
2. Sniff on loopback0 interface
3. Select the 1st TCP package, i.e. specify server port like 8980
4. Click on Save

### Visual VM tip

```aidl
export MAVEN_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=1100"
```

Then start Java process and attach to local VM port 1100

### How to:
0. Compile project
```aidl
mvn clean compile
```
1. Run streaming project. Server: 8980
```aidl
mvn exec:java -Dexec.mainClass=com.baeldung.grpc.streaming.StockServer
```
In other terminal
```aidl
mvn exec:java -Dexec.mainClass=com.baeldung.grpc.streaming.StockClient
```
2. Run Unary project
```aidl
mvn exec:java -Dexec.mainClass=com.baeldung.grpc.server.GrpcServer
```
In other terminal
```aidl
mvn exec:java -Dexec.mainClass=com.baeldung.grpc.client.GrpcClient
```
