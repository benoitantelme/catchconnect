package org.catchconnect;

import org.catchconnect.storage.RedisConnector;

/**
 *
 *
 */
public class App 
{
    public static void main(String[] args )
    {
        String ip = "192.170.20.46";
        RedisConnector connector = new RedisConnector(6379);

        for(int i = 0; i < 20; i++)
            connector.incrementIp(ip);

        System.out.println("IP " + ip + " seen " + connector.getIpOccurence(ip) + " times");

        connector.close();

        System.out.println( "Done" );
    }
}
