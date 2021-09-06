package com.iwares.qbatis;

public class Sort {

    public final String column;

    public final String algo;

    public final Iterable<?> fields;

    public Sort(String column, Iterable<?> fields, String algo) {
        this.column = column;
        this.algo = algo;
        this.fields = fields;
    }

    public Sort(String column, String algo) {
        this(column, null, algo);
    }

    public Sort(String column) {
        this(column, null, "");
    }

}
