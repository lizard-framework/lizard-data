package io.lizardframework.data.admin.repository;

import io.lizardframework.data.admin.repository.entity.ResourcesApplicationEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourcesApplicationRepository {
    int deleteByPrimaryKey(Long id);

    int insert(ResourcesApplicationEntity record);

    int insertSelective(ResourcesApplicationEntity record);

    ResourcesApplicationEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ResourcesApplicationEntity record);

    int updateByPrimaryKey(ResourcesApplicationEntity record);
}