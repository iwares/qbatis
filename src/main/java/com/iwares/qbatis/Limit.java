package com.iwares.qbatis;

public class Limit {

    public final Integer offset;
    public final Integer count;

    public Limit(Integer offset, Integer count) {
        this.offset = offset;
        this.count = count;
    }

    public Limit(Integer count) {
        this.offset = null;
        this.count = count;
    }

}
