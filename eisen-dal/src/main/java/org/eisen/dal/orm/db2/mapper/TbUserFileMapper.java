package org.eisen.dal.orm.db2.mapper;

import java.util.List;
import org.eisen.dal.orm.db2.model.TbUserFile;

public interface TbUserFileMapper {
    int deleteByPrimaryKey(Integer dataId);

    int insert(TbUserFile record);

    TbUserFile selectByPrimaryKey(Integer dataId);

    List<TbUserFile> selectAll();

    int updateByPrimaryKey(TbUserFile record);
}