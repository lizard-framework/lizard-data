package io.lizardframework.data.admin.repository;

import io.lizardframework.data.admin.repository.entity.MixedDatasourceAtomEntity;

public interface MixedDatasourceAtomDAO {
    int deleteByPrimaryKey(Long id);

    int insert(MixedDatasourceAtomEntity record);

    int insertSelective(MixedDatasourceAtomEntity record);

    MixedDatasourceAtomEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MixedDatasourceAtomEntity record);

    int updateByPrimaryKey(MixedDatasourceAtomEntity record);
}