package org.catchconnect.storage;

import org.junit.Test;
import redis.embedded.RedisServer;

import static org.junit.Assert.assertEquals;

public class RedisConnectorTest {
    private final String ip = "192.170.20.46";
    private final int port = 6379;

    @Test
    public void testIncrementingValues() {
        RedisServer redisServer = null;
        RedisConnector connector = null;
        
        try {
            redisServer = new RedisServer(port);
            redisServer.start();

            connector = new RedisConnector(port);
            for (int i = 0; i < 20; i++)
                connector.incrementIp(ip);

            Double a = connector.getIpOccurence(ip);
            assertEquals(20.0d, connector.getIpOccurence(ip), 0.1);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            connector.close();
            redisServer.stop();
        }
    }






}
