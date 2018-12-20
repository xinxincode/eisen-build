package org.eisen.dao.orm.db1.mapper;

import java.util.List;
import org.eisen.dao.orm.db1.model.TbFileDetail;

public interface TbFileDetailMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(TbFileDetail record);

    TbFileDetail selectByPrimaryKey(Integer fileId);

    List<TbFileDetail> selectAll();

    int updateByPrimaryKey(TbFileDetail record);
}