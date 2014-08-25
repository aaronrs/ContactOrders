package net.astechdesign.clients.repo;

public class Field {


    private final String name;
    private final String type;
    private Sequence sequence;
    private String primaryKey;

    public Field(String tablename, boolean key, String[] def) {
        name = def[0];
        int length = getNumber(def[1]);
        if (length > 0) {
            type = " VARCHAR(" + length + ")";
        } else {
            type = " " + def[1];
        }
        if (def.length > 2) {
            sequence = new Sequence(tablename);
        }
        if (key) {
            primaryKey = " PRIMARY KEY";
        }
    }

    private int getNumber(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {}
        return 0;
    }

    @Override
    public String toString() {
        return name + type + sequence + primaryKey;
    }

    public boolean isSequence() {
        return sequence != null;
    }
}
