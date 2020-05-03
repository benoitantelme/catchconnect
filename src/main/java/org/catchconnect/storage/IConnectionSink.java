package org.catchconnect.storage;

import java.util.concurrent.CompletableFuture;

public interface IConnectionSink {

    CompletableFuture<Boolean> receiveConnection(CompletableFuture<String> connection);
}
