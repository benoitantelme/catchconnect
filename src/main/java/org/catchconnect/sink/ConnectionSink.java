package org.catchconnect.sink;

import org.catchconnect.storage.IConnector;
import org.catchconnect.storage.RedisConnector;

import java.util.concurrent.CompletableFuture;

public class ConnectionSink implements IConnectionSink {
    IConnector connector = new RedisConnector(6379);

    public ConnectionSink(IConnector connector) {
        this.connector = connector;
    }

    @Override
    public void receiveConnection(CompletableFuture<String> connection) {
        //        TODO
    }
}
