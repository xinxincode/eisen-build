package org.eisen.dao.orm.mapper;

import java.util.List;
import org.eisen.dao.orm.model.TbFileDetail;

public interface TbFileDetailMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(TbFileDetail record);

    TbFileDetail selectByPrimaryKey(Integer fileId);

    List<TbFileDetail> selectAll();

    int updateByPrimaryKey(TbFileDetail record);
}