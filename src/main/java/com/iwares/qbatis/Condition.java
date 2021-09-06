package com.iwares.qbatis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Condition {

    private static String[] COMMON_OPS = { ">", "<", "=", "<>", "!=", ">=", "<=", "like" };

    private static String[] ARRAY_OPS = { "in", "not in" };

    private static String[] BETWEEN_OPS = { "between" };

    private static String[] NULLABLE_OPS = { "=", "<>", "!=" };

    private static String[] SORT_ALGOS = { "desc", "asc", "" };

    private static class Where {

        private String logic;

        private String lp;

        private String column;

        private String op;

        private String arg;

        private String rp;

        public Where(String logic, String lp, String column, String op, String arg, String rp) {
            setLogic(logic);
            setLp(lp);
            setColumn(column);
            setOp(op);
            setArg(arg);
            setRp(rp);
        }

        @SuppressWarnings("unused")
        public String getLogic() {
            return logic;
        }

        public void setLogic(String logic) {
            this.logic = logic;
        }

        @SuppressWarnings("unused")
        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        @SuppressWarnings("unused")
        public String getLp() {
            return lp;
        }

        public void setLp(String lp) {
            this.lp = lp;
        }

        @SuppressWarnings("unused")
        public String getOp() {
            return op;
        }

        public void setOp(String op) {
            this.op = op;
        }

        @SuppressWarnings("unused")
        public String getArg() {
            return arg;
        }

        public void setArg(String arg) {
            this.arg = arg;
        }

        @SuppressWarnings("unused")
        public String getRp() {
            return rp;
        }

        public void setRp(String rp) {
            this.rp = rp;
        }

    }

    private static class Group {

        private String column;

        public Group(String column) {
            setColumn(column);
        }

        @SuppressWarnings("unused")
        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

    }

    private static class Sort {

        private String column;

        private String algo;

        private List<String> fields;

        public Sort(String column, String algo, List<String> fields) {
            setColumn(column);
            setAlgo(algo);
            setFields(fields);
        }

        @SuppressWarnings("unused")
        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        @SuppressWarnings("unused")
        public String getAlgo() {
            return algo;
        }

        public void setAlgo(String algo) {
            this.algo = algo;
        }

        @SuppressWarnings("unused")
        public Iterable<?> getFields() {
            return fields;
        }

        public void setFields(List<String> fields) {
            this.fields = fields;
        }

    }

    private static class Limit {

        private String offset;

        private String count;

        public Limit(String offset, String count) {
            setOffset(offset);
            setCount(count);
        }

        @SuppressWarnings("unused")
        public String getOffset() {
            return offset;
        }

        public void setOffset(String offset) {
            this.offset = offset;
        }

        @SuppressWarnings("unused")
        public String getCount() {
            return count;
        }

        public void setCount(String size) {
            this.count = size;
        }

    }

    private ArrayList<Where> wheres = null;

    private ArrayList<Group> groups = null;

    private ArrayList<Sort> sorts = null;

    private Limit limit = null;

    public ArrayList<Where> getWheres() {
        return wheres;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public ArrayList<Sort> getSorts() {
        return sorts;
    }

    public Limit getLimit() {
        return limit;
    }

    private void checkColumnName(String column) {
        if (column != null && column.matches("^[_a-zA-Z][_\\.0-9a-zA-Z]*$"))
            return;
        throw new IllegalArgumentException("Invalid column name: " + column);
    }

    private void checkOperator(String op, String[] operators) {
        for (String operator : operators)
            if (operator.equals(op))
                return;
        throw new IllegalArgumentException("Invalid operator: " + op);
    }

    private void checkSortAlgorithm(String algo) {
        for (String alogrithm : SORT_ALGOS)
            if (alogrithm.equals(algo))
                return;
        throw new IllegalArgumentException("Invalid algorithm: " + algo);
    }

    private void checkLimit(Integer offset, Integer count) {
        if (offset != null && offset < 0)
            throw new IllegalArgumentException("Invalid offset: " + offset);
        if (count == null || count < 1)
            throw new IllegalArgumentException("Invalid count: " + count);
    }

    private void addWhere(Where where) {
        if (wheres == null)
            wheres = new ArrayList<Where>();
        wheres.add(where);
    }

    private static final SimpleDateFormat SQLDATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String toSQLArgument(Object arg) {
        if (arg instanceof Date) {
            return '"' + SQLDATEFORMAT.format((Date) arg) + '"';
        } else if (arg instanceof Number) {
            return arg.toString();
        } else if (arg instanceof String) {
            return '"' + ((String) arg).replace("\"", "\\\"") + '"';
        } else if (arg instanceof Boolean) {
            return (Boolean) arg ? "1" : "0";
        } else {
            return '"' + arg.toString().replace("\"", "\\\"") + '"';
        }
    }

    void addWhere(String logic, String column, String op, Object arg, String lb, String rb) {
        checkColumnName(column);

        String sqlArg = "";
        if (arg == null) {
            checkOperator(op, NULLABLE_OPS);
            op = "=".equals(op) ? "is null" : "is not null";
            sqlArg = "";
        } else if (arg instanceof Iterable<?>) {
            checkOperator(op, ARRAY_OPS);
            StringBuilder builder = new StringBuilder();
            builder.append('(');
            String separator = "";
            for (Object value : (Iterable<?>) arg) {
                builder.append(separator);
                builder.append(toSQLArgument(value));
                separator = ",";
            }
            builder.append(')');
            sqlArg = builder.toString();
        } else if (arg instanceof Range<?>) {
            Range<?> range = (Range<?>) arg;
            Object min = range.minimum;
            Object max = range.maximum;
            checkOperator(op, BETWEEN_OPS);
            sqlArg = toSQLArgument(min) + " and " + toSQLArgument(max);
        } else {
            checkOperator(op, COMMON_OPS);
            sqlArg = toSQLArgument(arg);
        }

        Where where = new Where(logic, lb, column, op, sqlArg, rb);
        addWhere(where);
    }

    void addWhere(String logic, String column, String op, Object arg) {
        addWhere(logic, column, op, arg, "", "");
    }

    private void addGroup(Group group) {
        if (groups == null)
            groups = new ArrayList<Condition.Group>();
        groups.add(group);
    }

    void addGroup(String column) {
        checkColumnName(column);
        Group group = new Group(column);
        addGroup(group);
    }

    private void addSort(Sort sort) {
        if (sorts == null)
            sorts = new ArrayList<Condition.Sort>();
        sorts.add(sort);
    }

    void addSort(String column, Iterable<?> fields, String algo) {
        checkColumnName(column);
        checkSortAlgorithm(algo);

        List<String> fieldlist = new ArrayList<>();
        if (fields != null)
            for (Object field : fields)
                fieldlist.add(toSQLArgument(field));

        if (fieldlist.isEmpty())
            fieldlist = null;

        Sort sort = new Sort(column, algo, fieldlist);
        addSort(sort);
    }

    void setLimit(Integer offset, Integer count) {
        checkLimit(offset, count);
        Limit limit = new Limit(offset != null ? offset.toString() : null, count.toString());
        this.limit = limit;
    }

    void setLimit(Integer count) {
        setLimit(null, count);
    }

}
