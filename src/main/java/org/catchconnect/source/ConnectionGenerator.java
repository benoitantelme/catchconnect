package org.catchconnect.source;

import org.catchconnect.sink.IConnectionSink;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConnectionGenerator implements IConnectionGenerator {
    protected static final String DOT = ".";
    protected IConnectionSink sink;

    public ConnectionGenerator(IConnectionSink sink) {
        this.sink = sink;
    }

    @Override
    public void generateIps(int n) {
        CompletableFuture.runAsync(() ->
                IntStream.range(0, n).forEach(nbr -> CompletableFuture.supplyAsync(this::getIp)));
        IntStream.range(0, n).forEach(nbr -> sink.receiveConnection(CompletableFuture.supplyAsync(this::getIp)));
    }

    protected String getIp() {
        return Arrays.stream(new int[]{ThreadLocalRandom.current().nextInt(224),
                ThreadLocalRandom.current().nextInt(256),
                ThreadLocalRandom.current().nextInt(256),
                ThreadLocalRandom.current().nextInt(256)}).boxed().
                map(n -> String.valueOf(n)).
                collect(Collectors.joining(DOT));
    }

}
