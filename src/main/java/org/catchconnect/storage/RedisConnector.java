package org.catchconnect.storage;

import org.catchconnect.model.IpStat;
import redis.clients.jedis.*;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class RedisConnector implements IConnector, IConnectionSink{
    private static final String PREFIX = "connections/ip/";

    private JedisPool jedisPool;

    public RedisConnector(int port) {
        jedisPool = new JedisPool(buildPoolConfig(), "localhost", port);
    }

    @Override
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

    @Override
    public int getIpOccurrence(String ip){
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

        return result.intValue();
    }

    @Override
    public List<IpStat> getTopK(int k){
        Jedis jedis ;
        Set<Tuple> tempResult = null;

        try {
            jedis = jedisPool.getResource();
            if (jedis != null){
                tempResult = jedis.zrevrangeByScoreWithScores(PREFIX, Integer.MAX_VALUE, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tempResult.stream().
                limit(k).
                map(tpl -> new IpStat(tpl.getElement(), (int) tpl.getScore())).
                collect(Collectors.toList());
    }

    @Override
    public void close(){
        jedisPool.close();
    }

    @Override
    public CompletableFuture<Boolean> receiveConnection(CompletableFuture<String> connection) {
        return connection.thenApply(ip -> incrementIp(ip));
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
