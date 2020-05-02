package org.catchconnect.storage;

import org.catchconnect.model.IpStat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

        List<IpStat> topK = connector.getTopK(5);
        assertEquals(5, topK.size());

        topK = connector.getTopK(20);
        assertEquals(20, topK.size());
        assertEquals(10, topK.get(0).getOccurrences());
        assertEquals(9, topK.get(1).getOccurrences());
        assertEquals(1, topK.get(2).getOccurrences());
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
