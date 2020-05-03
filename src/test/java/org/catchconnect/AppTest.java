package org.catchconnect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.catchconnect.source.ConnectionGenerator;
import org.catchconnect.storage.RedisConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.embedded.RedisServer;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Unit test for workflow
 */
public class AppTest {
    private final int port = 6379;
    RedisServer redisServer;
    RedisConnector connector;

    @Test
    public void worfklowTest() throws ExecutionException, InterruptedException {
        ConnectionGenerator generator = new ConnectionGenerator(connector);
        generator.generateIps(100);

        List<CompletableFuture<Boolean>> futures =  generator.generateIps(10);
        for(CompletableFuture<Boolean> future : futures)
            assertEquals(true, future.get());

        assertTrue(connector.getTopK(10).size() == 10);
    }

    @Before
    public void setUp() {
        redisServer = new RedisServer(port);
        redisServer.start();

        connector = new RedisConnector(port);
    }

    @After
    public void tearDown() {
        connector.close();
        redisServer.stop();
    }
}
