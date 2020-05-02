package org.catchconnect.storage;

import org.catchconnect.model.IpStat;

import java.util.List;

public interface IConnector {

    boolean incrementIp(String ip);
    int getIpOccurrence(String ip);
    List<IpStat> getTopK(int k);
    void close();

}
