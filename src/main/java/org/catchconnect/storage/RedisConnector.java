package org.catchconnect.storage;

import redis.clients.jedis.*;

import java.time.Duration;

public class RedisConnector {
    private static final String PREFIX = "connections/ip/";

    private JedisPool jedisPool;

    public RedisConnector(int port) {
        jedisPool = new JedisPool(buildPoolConfig(), "localhost", port);
    }

    public boolean incrementIp(String ip){
        Jedis jedis ;
        boolean success;
        try {
            jedis = jedisPool.getResource();
            if (jedis == null)
                success = false;
            else {
                Double updated = jedis.zincrby(PREFIX,1,ip);
                System.out.println(ip + " updated to " + updated);
                success = true;
            }
        } catch (Exception e) {
            success = false;
        }

        return success;
    }

    public Double getIpOccurence(String ip){
        Jedis jedis ;
        Double result = null;

        try {
            jedis = jedisPool.getResource();
            if (jedis != null){
                result = jedis.zscore(PREFIX, ip);
                System.out.println(ip + " occurences: " + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }



    public void close(){
        jedisPool.close();
    }

    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }


}
