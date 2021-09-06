package com.iwares.qbatis;

import java.util.ArrayList;
import java.util.List;

public class Param<Type> {

    public static final int TYPE_NULL = 0;
    public static final int TYPE_VALUE = 1;
    public static final int TYPE_ARRAY = 2;
    public static final int TYPE_RANGE = 3;

    private int type;

    private boolean invert;

    private Type value;

    private List<Type> array;

    private Type min;

    private Type max;

    private boolean minClose;

    private boolean maxClose;

    public static <Type> Param<Type> fromNull(boolean invert) {
        Param<Type> param = new Param<>();
        param.setType(Param.TYPE_NULL);
        param.setInvert(invert);
        return param;
    }

    public static <Type> Param<Type> fromNull() {
        return fromNull(false);
    }

    public static <Type> Param<Type> fromValue(Type value, boolean invert) {
        Param<Type> param = new Param<>();
        param.setType(Param.TYPE_VALUE);
        param.setValue(value);
        param.setInvert(invert);
        return param;
    }

    public static <Type> Param<Type> fromValue(Type value) {
        return fromValue(value, false);
    }

    public static <Type> Param<Type> fromArray(List<Type> list, boolean invert) {
        Param<Type> param = new Param<>();
        param.setType(Param.TYPE_ARRAY);
        param.setArray(list);
        param.setInvert(invert);
        return param;
    }

    public static <Type> Param<Type> fromArray(List<Type> list) {
        return fromArray(list, false);
    }

    public static <Type> Param<Type> fromArray(Type[] array, boolean invert) {
        List<Type> list = new ArrayList<>(array.length);
        for (Type item : array)
            list.add(item);
        Param<Type> param = new Param<>();
        param.setType(Param.TYPE_ARRAY);
        param.setArray(list);
        param.setInvert(invert);
        return param;
    }

    public static <Type> Param<Type> fromArray(Type[] array) {
        return fromArray(array, false);
    }

    public static <Type> Param<Type> fromRange(Type min, boolean minClose, Type max, boolean maxClose, boolean invert) {
        Param<Type> param = new Param<>();
        param.setType(Param.TYPE_RANGE);
        param.setMin(min);
        param.setMinClose(minClose);
        param.setMax(max);
        param.setMaxClose(maxClose);
        param.setInvert(invert);
        return param;
    }

    public static <Type> Param<Type> fromRange(Type min, boolean minClose, Type max, boolean maxClose) {
        return fromRange(min, minClose, max, maxClose, false);
    }

    public int getType() {
        return type;
    }

    void setType(int type) {
        this.type = type;
    }

    public boolean isInvert() {
        return invert;
    }

    void setInvert(boolean invert) {
        this.invert = invert;
    }

    public Type getValue() {
        return value;
    }

    void setValue(Type value) {
        this.value = value;
    }

    public List<Type> getArray() {
        return array;
    }

    void setArray(List<Type> array) {
        this.array = array;
    }

    public Type getMin() {
        return min;
    }

    void setMin(Type min) {
        this.min = min;
    }

    public Type getMax() {
        return max;
    }

    void setMax(Type max) {
        this.max = max;
    }

    public boolean isMinClose() {
        return minClose;
    }

    void setMinClose(boolean minClose) {
        this.minClose = minClose;
    }

    public boolean isMaxClose() {
        return maxClose;
    }

    void setMaxClose(boolean maxClose) {
        this.maxClose = maxClose;
    }

}
