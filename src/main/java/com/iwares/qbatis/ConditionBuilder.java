package com.iwares.qbatis;

import java.util.List;

import com.iwares.qbatis.SubWhere.Where;

public class ConditionBuilder {

    private static final int STATE_INITIAL = 0;

    private static final int STATE_WHERE = 1;

    private static final int STATE_GROUP = 2;

    private static final int STATE_SORT = 3;

    private static final int STATE_LIMIT = 4;

    private static final int STATE_FINISH = 5;

    private static final int[] WHERE_STATES = { STATE_INITIAL };

    private static final int[] ANDOR_STATES = { STATE_WHERE };

    private static final int[] GROUP_STATES = { STATE_INITIAL, STATE_WHERE, STATE_GROUP };

    private static final int[] SORT_STATES = { STATE_INITIAL, STATE_WHERE, STATE_GROUP, STATE_SORT };

    private static final int[] LIMIT_STATES = { STATE_INITIAL, STATE_WHERE, STATE_GROUP, STATE_SORT };

    private static final int[] BUILD_STATES = { STATE_INITIAL, STATE_WHERE, STATE_GROUP, STATE_SORT, STATE_LIMIT,
            STATE_FINISH };

    private final Condition condition = new Condition();

    private int state;

    private void checkState(int[] states) {
        for (int state : states)
            if (this.state == state)
                return;
        throw new IllegalStateException("Invalid state: " + state);
    }

    public ConditionBuilder where(String column, String op, Object arg) {
        checkState(WHERE_STATES);
        condition.addWhere("", column, op, arg);
        state = STATE_WHERE;
        return this;
    }

    public ConditionBuilder and(String column, String op, Object arg) {
        checkState(ANDOR_STATES);
        condition.addWhere("and", column, op, arg);
        return this;
    }

    public ConditionBuilder or(String column, String op, Object arg) {
        checkState(ANDOR_STATES);
        condition.addWhere("or", column, op, arg);
        return this;
    }

    private void addWhere(String logic, String[] columns, String op, Object arg, String lp, String rp) {
        if (columns.length <= 1)
            throw new IllegalArgumentException("\"columns\" must has 2 elements at least.");

        condition.addWhere(logic, columns[0], op, arg, lp + "(", "");
        int last = columns.length - 1;
        for (int i = 1; i < last; ++i)
            condition.addWhere("or", columns[i], op, arg);
        condition.addWhere("or", columns[last], op, arg, "", ")" + rp);
    }

    public ConditionBuilder where(String[] columns, String op, Object arg) {
        checkState(WHERE_STATES);

        addWhere("", columns, op, arg, "", "");

        state = STATE_WHERE;
        return this;
    }

    public ConditionBuilder and(String[] columns, String op, Object arg) {
        checkState(ANDOR_STATES);

        addWhere("and", columns, op, arg, "", "");

        state = STATE_WHERE;
        return this;
    }

    public ConditionBuilder or(String[] columns, String op, Object arg) {
        checkState(ANDOR_STATES);

        addWhere("or", columns, op, arg, "", "");

        state = STATE_WHERE;
        return this;
    }

    private void addWhere(String logic, String column, Param<?> param, String lp, String rp) {
        Object value = param.getValue();
        List<?> array = param.getArray();
        Object min = param.getMin();
        Object max = param.getMax();
        boolean invert = param.isInvert();
        boolean minClose = param.isMinClose();
        boolean maxClose = param.isMaxClose();

        switch (param.getType()) {
            case Param.TYPE_NULL:
                condition.addWhere(logic, column, invert ? "!=" : "=", null, lp, rp);
                break;
            case Param.TYPE_VALUE:
                condition.addWhere(logic, column, invert ? "!=" : "=", value, lp, rp);
                break;
            case Param.TYPE_ARRAY:
                condition.addWhere(logic, column, invert ? "not in" : "in", array, lp, rp);
                break;
            case Param.TYPE_RANGE:
                if (min != null) {
                    String op = invert ? (minClose ? "<" : "<=") : (minClose ? ">=" : ">");
                    condition.addWhere(logic, column, op, min, lp + (max != null ? "(" : ""), "");
                    logic = invert ? "or" : "and";
                }
                if (max != null) {
                    String op = invert ? (maxClose ? ">" : ">=") : (maxClose ? "<=" : "<");
                    condition.addWhere(logic, column, op, max, "", (min != null ? ")" : null) + rp);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid param type: " + param.getType());
        }
    }

    public ConditionBuilder where(SubWhere wheres) {
        checkState(WHERE_STATES);
        addWhere("", wheres, "", "");
        state = STATE_WHERE;
        return this;
    }

    public ConditionBuilder and(SubWhere wheres) {
        checkState(ANDOR_STATES);
        addWhere("and", wheres, "", "");
        state = STATE_WHERE;
        return this;
    }

    public ConditionBuilder or(SubWhere wheres) {
        checkState(ANDOR_STATES);
        addWhere("and", wheres, "", "");
        state = STATE_WHERE;
        return this;
    }

    public ConditionBuilder where(String column, Param<?> param) {
        checkState(WHERE_STATES);
        addWhere("", column, param, "", "");
        state = STATE_WHERE;
        return this;
    }

    public ConditionBuilder and(String column, Param<?> param) {
        checkState(ANDOR_STATES);
        addWhere("and", column, param, "", "");
        return this;
    }

    public ConditionBuilder or(String column, Param<?> param) {
        checkState(ANDOR_STATES);
        addWhere("or", column, param, "", "");
        return this;
    }

    private void addWhere(String logic, SubWhere wheres, String lp, String rp) {
        int last = wheres.size() - 1;
        if (last < 0)
            throw new RuntimeException("Wheres is empty.");

        Where head = wheres.get(0);
        if (last == 0) {
            addWhere(logic, head.column, head.op, head.arg, lp + head.lp, head.rp + rp);
            return;
        }

        addWhere(logic, head.column, head.op, head.arg, lp += "(" + head.lp, head.rp);

        for (int i = 1; i < last; ++i) {
            Where where = wheres.get(i);
            addWhere(where.logic, where.column, where.op, where.arg, where.lp, where.rp);
        }

        Where tail = wheres.get(last);
        addWhere(tail.logic, tail.column, tail.op, tail.arg, tail.lp, tail.rp + ")" + rp);
    }

    private void addWhere(String logic, String column, String op, Object arg, String lp, String rp) {
        if (arg != null && arg instanceof SubWhere) {
            addWhere(logic, (SubWhere) arg, lp, rp);
        } else if (arg != null && arg instanceof Param<?>) {
            addWhere(logic, column, (Param<?>) arg, lp, rp);
        } else {
            condition.addWhere(logic, column, op, arg, lp, rp);
        }
    }

    public ConditionBuilder group(String column) {
        checkState(GROUP_STATES);
        condition.addGroup(column);
        state = STATE_GROUP;
        return this;
    }

    public ConditionBuilder sort(String column, Iterable<?> fields, String algo) {
        checkState(SORT_STATES);
        condition.addSort(column, fields, algo);
        state = STATE_SORT;
        return this;
    }

    public ConditionBuilder sort(String column, Iterable<?> fields) {
        return this.sort(column, fields, "");
    }

    public ConditionBuilder sort(String column, String algo) {
        return this.sort(column, null, algo);
    }

    public ConditionBuilder sort(String column) {
        return this.sort(column, null, "");
    }

    public ConditionBuilder sort(List<Sort> sorts) {
        checkState(SORT_STATES);

        for (Sort sort : sorts)
            condition.addSort(sort.column, sort.fields, sort.algo);

        state = STATE_SORT;
        return this;
    }

    public ConditionBuilder limit(Integer offset, Integer count) {
        checkState(LIMIT_STATES);
        condition.setLimit(offset, count);
        state = STATE_LIMIT;
        return this;
    }

    public ConditionBuilder limit(Integer count) {
        checkState(LIMIT_STATES);
        condition.setLimit(count);
        state = STATE_LIMIT;
        return this;
    }

    public ConditionBuilder limit(Limit limit) {
        if (limit.offset != null || limit.count != null)
            this.limit(limit.offset, limit.count);
        return this;
    }

    public Condition build() {
        checkState(BUILD_STATES);
        state = STATE_FINISH;
        return condition;
    }

}
