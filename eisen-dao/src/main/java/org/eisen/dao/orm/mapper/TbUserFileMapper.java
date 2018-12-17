package org.eisen.dao.orm.mapper;

import java.util.List;
import org.eisen.dao.orm.model.TbUserFile;

public interface TbUserFileMapper {
    int deleteByPrimaryKey(Integer dataId);

    int insert(TbUserFile record);

    TbUserFile selectByPrimaryKey(Integer dataId);

    List<TbUserFile> selectAll();

    int updateByPrimaryKey(TbUserFile record);
}