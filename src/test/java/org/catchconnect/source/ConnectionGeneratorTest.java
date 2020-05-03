package org.catchconnect.source;

import org.catchconnect.storage.IConnectionSink;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.catchconnect.source.ConnectionGenerator.DOT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ConnectionGeneratorTest {
    ConnectionGenerator generator;

    @Test
    void ips() {
        IConnectionSink mockedSink = mock(IConnectionSink.class);
        generator = new ConnectionGenerator(mockedSink);
        List<CompletableFuture<String>> futures =  generator.generateIps(10);
        verify(mockedSink, times(10)).
                receiveConnection(any(CompletableFuture.class));
        assertEquals(10, futures.size());
    }

    @Test
    void getIp() {
        generator = new ConnectionGenerator(mock(IConnectionSink.class));
        String ip = generator.getIp();
        assertTrue(ip.contains(DOT));
        String[] splitString = ip.split("\\" + DOT);
        assertTrue(splitString.length == 4);

        for (String part : splitString)
            assertTrue(Integer.valueOf(part) <= 255);
    }
}