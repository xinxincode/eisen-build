package org.eisen.dal.orm.db1.mapper;

import org.eisen.dal.orm.db1.model.TbFileDetail;

import java.util.List;

public interface TbFileDetailMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(TbFileDetail record);

    TbFileDetail selectByPrimaryKey(Integer fileId);

    List<TbFileDetail> selectAll();

    int updateByPrimaryKey(TbFileDetail record);
}