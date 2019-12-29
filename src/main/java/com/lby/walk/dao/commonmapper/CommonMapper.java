package com.lby.walk.dao.commonmapper;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface CommonMapper<T> extends Mapper<T>, MySqlMapper<T>, IdsMapper<T> {

}