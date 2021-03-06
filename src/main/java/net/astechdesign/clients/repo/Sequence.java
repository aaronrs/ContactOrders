package net.astechdesign.clients.repo;

public class Sequence {

    public static final String SEQUENCE = " GENERATED BY DEFAULT AS SEQUENCE ";
    private final String tableName;

    public Sequence(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return SEQUENCE + tableName.toUpperCase() + "_SEQ";
    }
}
