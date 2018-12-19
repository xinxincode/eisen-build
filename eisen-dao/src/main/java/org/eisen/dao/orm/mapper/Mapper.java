package org.eisen.dao.orm.mapper;

import java.util.List;

public interface Mapper<T> {
    int deleteByPrimaryKey(Integer fileId);

    int insert(T record);

    T selectByPrimaryKey(Integer fileId);

    List<T> selectAll();

    int updateByPrimaryKey(T record);
}
