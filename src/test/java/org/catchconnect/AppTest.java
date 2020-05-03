package org.catchconnect;

import static org.junit.Assert.assertTrue;

import org.catchconnect.source.ConnectionGenerator;
import org.catchconnect.storage.RedisConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.embedded.RedisServer;

/**
 * Unit test for workflow
 */
public class AppTest {
    private final int port = 6379;
    RedisServer redisServer;
    RedisConnector connector;

    @Test
    public void worfklowTest() {
        ConnectionGenerator generator = new ConnectionGenerator(connector);


        generator.generateIps(100);

        assertTrue(connector.getTopK(10).size() == 10);


        assertTrue(true);
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
