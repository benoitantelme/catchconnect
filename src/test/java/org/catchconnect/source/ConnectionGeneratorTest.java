package org.catchconnect.source;

import org.catchconnect.sink.ConnectionSink;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.catchconnect.source.ConnectionGenerator.DOT;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ConnectionGeneratorTest {
    ConnectionGenerator generator;

    @Test
    void ips() {
        ConnectionSink mockedSink = mock(ConnectionSink.class);
        generator = new ConnectionGenerator(mockedSink);
        generator.generateIps(10);
        verify(mockedSink, times(10)).
                receiveConnection(any(CompletableFuture.class));
    }

    @Test
    void getIp() {
        generator = new ConnectionGenerator(mock(ConnectionSink.class));
        String ip = generator.getIp();
        assertTrue(ip.contains(DOT));
        String[] splitString = ip.split("\\" + DOT);
        assertTrue(splitString.length == 4);

        for (String part : splitString)
            assertTrue(Integer.valueOf(part) <= 255);
    }
}