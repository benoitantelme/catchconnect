package org.catchconnect.source;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IConnectionGenerator {
    List<CompletableFuture<String>> generateIps(int n);
}
