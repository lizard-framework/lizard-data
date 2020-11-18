package io.lizardframework.data.admin.repository;

import io.lizardframework.data.admin.repository.entity.MixedDataSourceEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MixedDataSourceDAO {
	int deleteByPrimaryKey(Long id);

	int insert(MixedDataSourceEntity record);

	int insertSelective(MixedDataSourceEntity record);

	MixedDataSourceEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(MixedDataSourceEntity record);

	int updateByPrimaryKey(MixedDataSourceEntity record);

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
	List<MixedDataSourceEntity> selectPage(@Param("params") Map<String, Object> params, @Param("pageable") Pageable pageable);

	/**
	 * 根据mixedName查询记录
	 *
	 * @param mixedName
	 * @return
	 */
	MixedDataSourceEntity selectByMixedName(@Param("mixedName") String mixedName);
}