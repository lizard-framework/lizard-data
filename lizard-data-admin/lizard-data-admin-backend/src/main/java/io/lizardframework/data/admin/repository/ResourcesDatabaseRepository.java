package io.lizardframework.data.admin.repository;

import io.lizardframework.data.admin.repository.entity.ResourcesDatabaseEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ResourcesDatabaseRepository {
	int deleteByPrimaryKey(Long id);

	int insert(ResourcesDatabaseEntity record);

	int insertSelective(ResourcesDatabaseEntity record);

	ResourcesDatabaseEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ResourcesDatabaseEntity record);

	int updateByPrimaryKey(ResourcesDatabaseEntity record);

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
	List<ResourcesDatabaseEntity> selectPage(@Param("params") Map<String, Object> params, @Param("pageable") Pageable pageable);
}