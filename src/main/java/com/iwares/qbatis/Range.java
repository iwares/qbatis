package com.iwares.qbatis;

public class Range<Type> {

    public final Type minimum;

    public final Type maximum;

    public Range(Type minimum, Type maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

}
