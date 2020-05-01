package org.catchconnect.sink;

import java.util.concurrent.CompletableFuture;

public interface IConnectionSink {

    void receiveConnection(CompletableFuture<String> connection);

}
