package io.lizardframework.data.admin.repository;

import io.lizardframework.data.admin.repository.entity.ResourcesApplicationEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ResourcesApplicationRepository {
	int deleteByPrimaryKey(Long id);

	int insert(ResourcesApplicationEntity record);

	int insertSelective(ResourcesApplicationEntity record);

	ResourcesApplicationEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ResourcesApplicationEntity record);

	int updateByPrimaryKey(ResourcesApplicationEntity record);

	/**
	 * 查询记录数
	 *
	 * @param params
	 * @return
	 */
	long selectCount(@Param("params") Map<String, Object> params);

	/**
	 * 分页查询记录
	 *
	 * @param params
	 * @param pageable
	 * @return
	 */
	List<ResourcesApplicationEntity> selectPage(@Param("params") Map<String, Object> params, @Param("pageable") Pageable pageable);
}