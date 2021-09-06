package com.iwares.qbatis;

import java.util.List;

public interface BaseDao<DataType, KeyType> {

    public int insert(DataType object);

    public int bulkInsert(List<DataType> objects);

    public int replace(DataType object);

    public DataType select(KeyType id);

    public List<DataType> selectConditional(Condition condition);

    public List<KeyType> selectIdsConditional(Condition condition);

    public int update(DataType object);

    public int count(Condition condition);

    public int delete(KeyType id);

    public int deleteConditional(Condition condition);

}
