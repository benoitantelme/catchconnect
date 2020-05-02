package org.catchconnect.model;

import java.util.Objects;

public class IpStat {
    private String ip;
    private int occurrences;

    public IpStat(String ip, int occurrences) {
        this.ip = ip;
        this.occurrences = occurrences;
    }

    @Override
    public String toString() {
        return "IpStat{" +
                "ip='" + ip + '\'' +
                ", occurrences=" + occurrences +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpStat ipStat = (IpStat) o;
        return ip.equals(ipStat.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }

    public String getIp() {
        return ip;
    }

    public int getOccurrences() {
        return occurrences;
    }
}
