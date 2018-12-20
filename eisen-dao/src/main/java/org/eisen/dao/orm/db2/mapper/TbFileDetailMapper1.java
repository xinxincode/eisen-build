package org.eisen.dao.orm.db2.mapper;

import org.eisen.dao.orm.db2.model.TbFileDetail;

import java.util.List;

public interface TbFileDetailMapper1 {
    int deleteByPrimaryKey(Integer fileId);

    int insert(TbFileDetail record);

    TbFileDetail selectByPrimaryKey(Integer fileId);

    List<TbFileDetail> selectAll();

    int updateByPrimaryKey(TbFileDetail record);
}