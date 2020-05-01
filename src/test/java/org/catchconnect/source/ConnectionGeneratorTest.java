package org.catchconnect.source;

import org.junit.jupiter.api.Test;

import static org.catchconnect.source.ConnectionGenerator.DOT;
import static org.junit.jupiter.api.Assertions.*;

class ConnectionGeneratorTest {
    ConnectionGenerator generator = new ConnectionGenerator();

    @Test
    void ips() {
    }


    @Test
    void getIp() {
        String ip = generator.getIp();
        assertTrue(ip.contains(DOT));
        String[] splitString = ip.split("\\" + DOT);
        assertTrue(splitString.length == 4);

        for(String part : splitString)
            assertTrue(Integer.valueOf(part) <= 255);
    }
}