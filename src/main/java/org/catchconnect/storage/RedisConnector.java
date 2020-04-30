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
                String key = PREFIX + ip;
                Long result = jedis.incr(key);
                System.out.println(key + " updated to " + result);
                success = true;
            }
        } catch (Exception e) {
            success = false;
        }

        return success;
    }

    public Long getIpOccurence(String ip){
        Jedis jedis ;
        Long result = null;

        try {
            jedis = jedisPool.getResource();
            if (jedis != null){
                String key = PREFIX + ip;
                String res = jedis.get(key);
                System.out.println(key + " updated to " + res);
                result = Long.valueOf(res);
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
