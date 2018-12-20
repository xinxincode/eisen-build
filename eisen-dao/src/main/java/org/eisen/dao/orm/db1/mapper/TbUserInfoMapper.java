package org.eisen.dao.orm.db1.mapper;

import java.util.List;
import org.eisen.dao.orm.db1.model.TbUserInfo;

public interface TbUserInfoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(TbUserInfo record);

    TbUserInfo selectByPrimaryKey(Integer userId);

    List<TbUserInfo> selectAll();

    int updateByPrimaryKey(TbUserInfo record);
}