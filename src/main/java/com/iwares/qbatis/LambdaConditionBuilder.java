package com.iwares.qbatis;

import java.util.List;

public class LambdaConditionBuilder<BeanType> extends ConditionBuilder {

    public LambdaConditionBuilder<BeanType> where(String column, String op, Object arg) {
        super.where(column, op, arg);
        return this;
    }

    public LambdaConditionBuilder<BeanType> and(String column, String op, Object arg) {
        super.and(column, op, arg);
        return this;
    }

    public LambdaConditionBuilder<BeanType> or(String column, String op, Object arg) {
        super.or(column, op, arg);
        return this;
    }

    public LambdaConditionBuilder<BeanType> where(String[] columns, String op, Object arg) {
        super.where(columns, op, arg);
        return this;
    }

    public LambdaConditionBuilder<BeanType> and(String[] columns, String op, Object arg) {
        super.and(columns, op, arg);
        return this;
    }

    public LambdaConditionBuilder<BeanType> or(String[] columns, String op, Object arg) {
        super.or(columns, op, arg);
        return this;
    }

    public LambdaConditionBuilder<BeanType> where(SubWhere wheres) {
        super.where(wheres);
        return this;
    }

    public LambdaConditionBuilder<BeanType> and(SubWhere wheres) {
        super.and(wheres);
        return this;
    }

    public LambdaConditionBuilder<BeanType> or(SubWhere wheres) {
        super.or(wheres);
        return this;
    }

    public LambdaConditionBuilder<BeanType> where(LambdaSubWhere<BeanType> wheres) {
        super.where(wheres);
        return this;
    }

    public LambdaConditionBuilder<BeanType> and(LambdaSubWhere<BeanType> wheres) {
        super.and(wheres);
        return this;
    }

    public LambdaConditionBuilder<BeanType> or(LambdaSubWhere<BeanType> wheres) {
        super.or(wheres);
        return this;
    }

    public LambdaConditionBuilder<BeanType> where(String column, Param<?> param) {
        super.where(column, param);
        return this;
    }

    public LambdaConditionBuilder<BeanType> and(String column, Param<?> param) {
        super.and(column, param);
        return this;
    }

    public LambdaConditionBuilder<BeanType> or(String column, Param<?> param) {
        super.or(column, param);
        return this;
    }

    public LambdaConditionBuilder<BeanType> group(String column) {
        super.group(column);
        return this;
    }

    public LambdaConditionBuilder<BeanType> sort(String column, Iterable<?> fields, String algo) {
        super.sort(column, fields, algo);
        return this;
    }

    public LambdaConditionBuilder<BeanType> sort(String column, Iterable<?> fields) {
        super.sort(column, fields);
        return this;
    }

    public LambdaConditionBuilder<BeanType> sort(String column, String algo) {
        super.sort(column, algo);
        return this;
    }

    public LambdaConditionBuilder<BeanType> sort(String column) {
        super.sort(column);
        return this;
    }

    public LambdaConditionBuilder<BeanType> sort(List<Sort> sorts) {
        super.sort(sorts);
        return this;
    }

    public LambdaConditionBuilder<BeanType> limit(Integer offset, Integer count) {
        super.limit(offset, count);
        return this;
    }

    public LambdaConditionBuilder<BeanType> limit(Integer count) {
        super.limit(count);
        return this;
    }

    public LambdaConditionBuilder<BeanType> limit(Limit limit) {
        super.limit(limit);
        return this;
    }

    public LambdaConditionBuilder<BeanType> where(PropFunction<BeanType, ?> func, String op, Object arg) {
        where(PropFunction.getPropName(func), op, arg);
        return this;
    }

    public LambdaConditionBuilder<BeanType> and(PropFunction<BeanType, ?> func, String op, Object arg) {
        and(PropFunction.getPropName(func), op, arg);
        return this;
    }

    public LambdaConditionBuilder<BeanType> or(PropFunction<BeanType, ?> func, String op, Object arg) {
        or(PropFunction.getPropName(func), op, arg);
        return this;
    }

    public LambdaConditionBuilder<BeanType> where(PropFunction<BeanType, ?>[] funcs, String op, Object arg) {
        String[] columns = new String[funcs.length];
        for (int i = 0; i < funcs.length; ++i)
            columns[i] = PropFunction.getPropName(funcs[i]);
        where(columns, op, arg);
        return this;
    }

    public LambdaConditionBuilder<BeanType> and(PropFunction<BeanType, ?>[] funcs, String op, Object arg) {
        String[] columns = new String[funcs.length];
        for (int i = 0; i < funcs.length; ++i)
            columns[i] = PropFunction.getPropName(funcs[i]);
        and(columns, op, arg);
        return this;
    }

    public LambdaConditionBuilder<BeanType> or(PropFunction<BeanType, ?>[] funcs, String op, Object arg) {
        String[] columns = new String[funcs.length];
        for (int i = 0; i < funcs.length; ++i)
            columns[i] = PropFunction.getPropName(funcs[i]);
        or(columns, op, arg);
        return this;
    }

    public LambdaConditionBuilder<BeanType> where(PropFunction<BeanType, ?> func, Param<?> param) {
        where(PropFunction.getPropName(func), param);
        return this;
    }

    public LambdaConditionBuilder<BeanType> and(PropFunction<BeanType, ?> func, Param<?> param) {
        and(PropFunction.getPropName(func), param);
        return this;
    }

    public LambdaConditionBuilder<BeanType> or(PropFunction<BeanType, ?> func, Param<?> param) {
        or(PropFunction.getPropName(func), param);
        return this;
    }

    public LambdaConditionBuilder<BeanType> group(PropFunction<BeanType, ?> func) {
        group(PropFunction.getPropName(func));
        return this;
    }

    public LambdaConditionBuilder<BeanType> sort(PropFunction<BeanType, ?> func, Iterable<?> fields, String algo) {
        sort(PropFunction.getPropName(func), fields, algo);
        return this;
    }

    public LambdaConditionBuilder<BeanType> sort(PropFunction<BeanType, ?> func, Iterable<?> fields) {
        sort(PropFunction.getPropName(func), fields);
        return this;
    }

    public LambdaConditionBuilder<BeanType> sort(PropFunction<BeanType, ?> func, String algo) {
        sort(PropFunction.getPropName(func), algo);
        return this;
    }

    public LambdaConditionBuilder<BeanType> sort(PropFunction<BeanType, ?> func) {
        sort(PropFunction.getPropName(func));
        return this;
    }

}
