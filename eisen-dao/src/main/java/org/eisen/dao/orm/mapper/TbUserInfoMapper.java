package org.eisen.dao.orm.mapper;

import java.util.List;
import org.eisen.dao.orm.model.TbUserInfo;

public interface TbUserInfoMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(TbUserInfo record);

    TbUserInfo selectByPrimaryKey(Integer userId);

    List<TbUserInfo> selectAll();

    int updateByPrimaryKey(TbUserInfo record);
}