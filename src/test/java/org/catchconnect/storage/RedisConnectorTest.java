package org.catchconnect.storage;

import org.junit.Test;
import redis.embedded.RedisServer;

import static org.junit.Assert.assertTrue;

public class RedisConnectorTest {
    private final String ip = "192.170.20.46";
    private final int port = 6379;

    @Test
    public void testIncrementingValues() {
        RedisServer redisServer = new RedisServer(port);
        redisServer.start();

        RedisConnector connector = new RedisConnector(port);
        for(int i = 0; i < 20; i++)
            connector.incrementIp(ip);


        assertTrue(20l == connector.getIpOccurence(ip));

        connector.close();
        redisServer.stop();
    }






}
