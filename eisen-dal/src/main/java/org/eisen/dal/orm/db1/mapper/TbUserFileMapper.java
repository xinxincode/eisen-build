package org.eisen.dal.orm.db1.mapper;

import java.util.List;
import org.eisen.dal.orm.db1.model.TbUserFile;

public interface TbUserFileMapper {
    int deleteByPrimaryKey(Integer dataId);

    int insert(TbUserFile record);

    TbUserFile selectByPrimaryKey(Integer dataId);

    List<TbUserFile> selectAll();

    int updateByPrimaryKey(TbUserFile record);
}