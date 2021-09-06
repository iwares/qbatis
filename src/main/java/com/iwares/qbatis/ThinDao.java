package com.iwares.qbatis;

import java.util.List;

public interface ThinDao<DataType, KeyType> {

    public DataType selectThin(KeyType id);

    public List<DataType> selectThinConditional(Condition condition);

}
