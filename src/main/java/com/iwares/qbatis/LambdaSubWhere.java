package com.iwares.qbatis;

public class LambdaSubWhere<BeanType> extends SubWhere {

    public LambdaSubWhere(String column, String op, Object arg) {
        super(column, op, arg);
    }

    public LambdaSubWhere(SubWhere subWheres) {
        super(subWheres);
    }

    public LambdaSubWhere(LambdaSubWhere<BeanType> subWheres) {
        super(subWheres);
    }

    public LambdaSubWhere(String column, Param<?> param) {
        super(column, param);
    }

    public LambdaSubWhere(PropFunction<BeanType, ?> func, String op, Object arg) {
        super(PropFunction.getPropName(func), op, arg);
    }

    public LambdaSubWhere(PropFunction<BeanType, ?> func, Param<?> param) {
        super(PropFunction.getPropName(func), param);
    }

    public LambdaSubWhere<BeanType> and(String column, String op, Object arg) {
        super.and(column, op, arg);
        return this;
    }

    public LambdaSubWhere<BeanType> or(String column, String op, Object arg) {
        super.or(column, op, arg);
        return this;
    }

    public LambdaSubWhere<BeanType> and(SubWhere subWheres) {
        super.and(subWheres);
        return this;
    }

    public LambdaSubWhere<BeanType> or(SubWhere subWheres) {
        super.or(subWheres);
        return this;
    }

    public LambdaSubWhere<BeanType> and(LambdaSubWhere<BeanType> subWheres) {
        super.and(subWheres);
        return this;
    }

    public LambdaSubWhere<BeanType> or(LambdaSubWhere<BeanType> subWheres) {
        super.or(subWheres);
        return this;
    }

    public LambdaSubWhere<BeanType> and(String column, Param<?> param) {
        super.and(column, param);
        return this;
    }

    public LambdaSubWhere<BeanType> or(String column, Param<?> param) {
        super.or(column, param);
        return this;
    }

    public LambdaSubWhere<BeanType> and(String[] columns, String op, Object arg) {
        super.and(columns, op, arg);
        return this;
    }

    public LambdaSubWhere<BeanType> or(String[] columns, String op, Object arg) {
        super.or(columns, op, arg);
        return this;
    }

    public LambdaSubWhere<BeanType> and(PropFunction<BeanType, ?> func, String op, Object arg) {
        and(PropFunction.getPropName(func), op, arg);
        return this;
    }

    public LambdaSubWhere<BeanType> or(PropFunction<BeanType, ?> func, String op, Object arg) {
        or(PropFunction.getPropName(func), op, arg);
        return this;
    }

    public LambdaSubWhere<BeanType> and(PropFunction<BeanType, ?> func, Param<?> param) {
        and(PropFunction.getPropName(func), param);
        return this;
    }

    public LambdaSubWhere<BeanType> or(PropFunction<BeanType, ?> func, Param<?> param) {
        or(PropFunction.getPropName(func), param);
        return this;
    }

    public LambdaSubWhere<BeanType> and(PropFunction<BeanType, ?>[] funcs, String op, Object arg) {
        String[] columns = new String[funcs.length];
        for (int i = 0; i < funcs.length; ++i)
            columns[i] = PropFunction.getPropName(funcs[i]);
        and(columns, op, arg);
        return this;
    }

    public LambdaSubWhere<BeanType> or(PropFunction<BeanType, ?>[] funcs, String op, Object arg) {
        String[] columns = new String[funcs.length];
        for (int i = 0; i < funcs.length; ++i)
            columns[i] = PropFunction.getPropName(funcs[i]);
        and(columns, op, arg);
        return this;
    }

}
