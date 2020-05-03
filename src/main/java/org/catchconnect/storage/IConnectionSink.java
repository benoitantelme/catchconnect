package org.catchconnect.storage;

import java.util.concurrent.CompletableFuture;

public interface IConnectionSink {

    void receiveConnection(CompletableFuture<String> connection);
}
