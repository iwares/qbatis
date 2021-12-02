package com.iwares.qbatis;

import java.util.ArrayList;
import java.util.List;

public class SubWhere {

    static class Where {

        public String logic;

        public String lp;

        public String column;

        public String op;

        public Object arg;

        public String rp;

        public Where(String logic, String lp, String column, String op, Object arg, String rp) {
            this.logic = logic;
            this.lp = lp;
            this.column = column;
            this.op = op;
            this.arg = arg;
            this.rp = rp;
        }

    }

    private List<Where> wheres = new ArrayList<>();

    Where get(int index) {
        return wheres.get(index);
    }

    int size() {
        return wheres.size();
    }

    public SubWhere(String column, String op, Object arg) {
        wheres.add(new Where("", "", column, op, arg, ""));
    }

    public SubWhere(SubWhere subWheres) {
        wheres.add(new Where("", "", "", "", subWheres, ""));
    }

    public SubWhere(String column, Param<?> param) {
        wheres.add(new Where("", "", column, "", param, ""));
    }

    public SubWhere and(String column, String op, Object arg) {
        wheres.add(new Where("and", "", column, op, arg, ""));
        return this;
    }

    public SubWhere or(String column, String op, Object arg) {
        wheres.add(new Where("or", "", column, op, arg, ""));
        return this;
    }

    public SubWhere and(SubWhere subWheres) {
        wheres.add(new Where("and", "", "", "", subWheres, ""));
        return this;
    }

    public SubWhere or(SubWhere subWheres) {
        wheres.add(new Where("or", "", "", "", subWheres, ""));
        return this;
    }

    public SubWhere and(String column, Param<?> param) {
        wheres.add(new Where("and", "", column, "", param, ""));
        return this;
    }

    public SubWhere or(String column, Param<?> param) {
        wheres.add(new Where("or", "", column, "", param, ""));
        return this;
    }

    private void addWhere(String logic, String[] columns, String op, Object arg) {
        if (columns.length <= 1)
            throw new IllegalArgumentException("\"columns\" must has 2 elements at least.");

        wheres.add(new Where(logic, "(", columns[0], op, arg, ""));
        int last = columns.length - 1;
        for (int i = 1; i < last; ++i)
            wheres.add(new Where("or", "", columns[i], op, arg, ""));
        wheres.add(new Where("or", "", columns[last], op, arg, ")"));
    }

    public SubWhere and(String[] columns, String op, Object arg) {
        addWhere("and", columns, op, arg);
        return this;
    }

    public SubWhere or(String[] columns, String op, Object arg) {
        addWhere("or", columns, op, arg);
        return this;
    }

}
