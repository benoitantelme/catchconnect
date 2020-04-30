package org.catchconnect.source;

import java.util.stream.Stream;

public interface IConnectionGenerator {
    Stream<String> ips();
}
