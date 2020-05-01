package org.catchconnect.source;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ConnectionGenerator implements IConnectionGenerator {
    public static final String DOT = ".";

    @Override
    public List<String> ips() {



        return null;
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
