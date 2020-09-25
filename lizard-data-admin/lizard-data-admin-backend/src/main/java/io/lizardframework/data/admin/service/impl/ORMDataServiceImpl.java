package io.lizardframework.data.admin.service.impl;

import io.lizardframework.data.admin.commons.BizException;
import io.lizardframework.data.admin.dao.OrmMixedDAO;
import io.lizardframework.data.admin.dao.OrmRepositoryDAO;
import io.lizardframework.data.admin.dao.entity.OrmMixedEntity;
import io.lizardframework.data.admin.dao.entity.OrmRepositoryAllInfoEntity;
import io.lizardframework.data.admin.message.RespMessage;
import io.lizardframework.data.admin.service.ORMDataService;
import io.lizardframework.data.enums.LoadBalanceType;
import io.lizardframework.data.enums.MasterSlaveType;
import io.lizardframework.data.enums.State;
import io.lizardframework.data.orm.enums.DBType;
import io.lizardframework.data.orm.enums.DataSourcePoolType;
import io.lizardframework.data.orm.model.AtomDataSourceModel;
import io.lizardframework.data.orm.model.MixedDataSourceModel;
import io.lizardframework.data.orm.model.RepositoryDataSourceModel;
import io.lizardframework.data.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xueqi
 * @date 2020-09-25
 */
@Service
@Slf4j
public class ORMDataServiceImpl implements ORMDataService {

	@Autowired
	private OrmMixedDAO      ormMixedDAO;
	@Autowired
	private OrmRepositoryDAO ormRepositoryDAO;


	@Override
	public MixedDataSourceModel queryMixedDataSource(String mixedDataSourceName) {
		// query mixed datasource
		OrmMixedEntity mixedEntity = ormMixedDAO.selectByMixedName(mixedDataSourceName);
		if (mixedEntity == null) {
			throw new BizException(RespMessage.ORM_MIXED_DATASOURCE_NOT_EXIST);
		}
		MixedDataSourceModel mixedDataSourceModel = new MixedDataSourceModel();
		mixedDataSourceModel.setMixedName(mixedDataSourceName);
		mixedDataSourceModel.setState(State.convert(mixedEntity.getState()));
		mixedDataSourceModel.setType(DBType.convert(mixedEntity.getDbType()));

		// query repository and atom
		List<OrmRepositoryAllInfoEntity> repositoryAllInfoEntityList = ormRepositoryDAO.selectRepositoryAllInfo(mixedEntity.getId());
		if (!CollectionUtils.isEmpty(repositoryAllInfoEntityList)) {
			Map<String, RepositoryDataSourceModel> repositoryDataSourceMap = new LinkedHashMap<>();

			repositoryAllInfoEntityList.forEach(entity -> {
				String repositoryName = entity.getRepositoryName();

				RepositoryDataSourceModel repositoryDataSourceModel = repositoryDataSourceMap.get(repositoryName);
				if (repositoryDataSourceModel == null) {
					repositoryDataSourceModel = new RepositoryDataSourceModel();
					repositoryDataSourceModel.setRepositoryName(repositoryName);
					repositoryDataSourceModel.setLoadBalance(LoadBalanceType.convert(entity.getLoadBalance()));
					repositoryDataSourceModel.setState(State.convert(entity.getRepositoryState()));
					repositoryDataSourceModel.setAtoms(new ArrayList<>());
				}

				AtomDataSourceModel atomDataSourceModel = new AtomDataSourceModel();
				atomDataSourceModel.setAtomName(entity.getAtomName());
				atomDataSourceModel.setHost(entity.getHost());
				atomDataSourceModel.setPort(entity.getPort());
				atomDataSourceModel.setUsername(entity.getUsername());
				atomDataSourceModel.setPassword(entity.getPassword());
				atomDataSourceModel.setDatabase(entity.getDatabase());
				atomDataSourceModel.setParams(entity.getJdbcParams());
				atomDataSourceModel.setMasterSlaveType(MasterSlaveType.convert(entity.getMasterSlaveType()));
				atomDataSourceModel.setState(State.convert(entity.getAtomState()));
				atomDataSourceModel.setWeight(entity.getWeight());
				atomDataSourceModel.setDataSourcePoolType(DataSourcePoolType.convert(entity.getDataSourcePoolType()));
				if (StringUtils.isNotEmpty(entity.getPoolConfig())) {
					atomDataSourceModel.setPoolConfigMapper(JSONUtils.json2Map(entity.getPoolConfig()));
				}

				repositoryDataSourceModel.getAtoms().add(atomDataSourceModel);

				repositoryDataSourceMap.put(repositoryName, repositoryDataSourceModel);
			});

			mixedDataSourceModel.setRepositories(repositoryDataSourceMap.values().stream().collect(Collectors.toList()));
		}

		return mixedDataSourceModel;
	}


}
