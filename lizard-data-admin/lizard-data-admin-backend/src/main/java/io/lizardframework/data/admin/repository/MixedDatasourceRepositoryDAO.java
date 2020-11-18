package io.lizardframework.data.admin.repository;

import io.lizardframework.data.admin.repository.entity.MixedDatasourceRepositoryEntity;
import io.lizardframework.data.admin.repository.entity.extend.DataSourceRepositoryAllInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MixedDatasourceRepositoryDAO {
	int deleteByPrimaryKey(Long id);

	int insert(MixedDatasourceRepositoryEntity record);

	int insertSelective(MixedDatasourceRepositoryEntity record);

	MixedDatasourceRepositoryEntity selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(MixedDatasourceRepositoryEntity record);

	int updateByPrimaryKey(MixedDatasourceRepositoryEntity record);

	/**
	 * 根据datasourceId查询所有repository及atoms配置
	 *
	 * @param datasourceId
	 * @return
	 */
	List<DataSourceRepositoryAllInfoEntity> selectAllInfoAndAtomByDataSourceId(@Param("datasourceId") Long datasourceId);
}