package com.iwares.qbatis;

import java.util.List;

public interface CplxDao<DataType, KeyType> {

    public DataType selectCplx(KeyType id);

    public List<DataType> selecCplxConditional(Condition condition);

    public int countCplx(Condition condition);

}
