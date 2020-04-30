package org.catchconnect.storage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Tuple;
import redis.embedded.RedisServer;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RedisConnectorTest {
    private final String ip = "192.170.20.46";
    private final int port = 6379;

    private RedisServer redisServer = null;
    private RedisConnector connector = null;

    @Test
    public void testIncrementingValues() {
        for (int i = 0; i < 20; i++)
            connector.incrementIp(ip);

        assertEquals(20.0d, connector.getIpOccurrence(ip), 0.1);
    }


    @Test
    public void testGetTokK() {
        for (int i = 0; i < 20; i++)
            connector.incrementIp(ip + i);

        for (int i = 0; i < 10; i++)
            connector.incrementIp(ip);

        for (int i = 0; i < 8; i++)
            connector.incrementIp(ip+1);

        List<Tuple> topK = connector.getTopK(5);
        assertEquals(5, topK.size());

        topK = connector.getTopK(20);
        assertEquals(20, topK.size());
        assertEquals(10.0, topK.get(0).getScore(), 0.1);
        assertEquals(9.0, topK.get(1).getScore(), 0.1);
        assertEquals(1.0, topK.get(2).getScore(), 0.1);
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
