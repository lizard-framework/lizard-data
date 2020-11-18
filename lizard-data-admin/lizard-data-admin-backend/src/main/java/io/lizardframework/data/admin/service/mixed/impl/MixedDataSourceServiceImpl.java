package io.lizardframework.data.admin.service.mixed.impl;

import io.lizardframework.data.admin.commons.BizException;
import io.lizardframework.data.admin.commons.pageable.PageResult;
import io.lizardframework.data.admin.controller.api.params.ORMGetMixedConfigParams;
import io.lizardframework.data.admin.controller.applicationConfig.datasource.params.MixedDataSourceListParam;
import io.lizardframework.data.admin.message.MessageEnum;
import io.lizardframework.data.admin.model.mixed.MixedDataSourceDetailModel;
import io.lizardframework.data.admin.model.mixed.MixedDataSourceInfoModel;
import io.lizardframework.data.admin.repository.MixedDataSourceDAO;
import io.lizardframework.data.admin.repository.MixedDatasourceRepositoryDAO;
import io.lizardframework.data.admin.repository.entity.MixedDataSourceEntity;
import io.lizardframework.data.admin.repository.entity.extend.DataSourceRepositoryAllInfoEntity;
import io.lizardframework.data.admin.service.mixed.MixedDataSourceService;
import io.lizardframework.data.admin.support.CodeTemplateSupport;
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
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xueqi
 * @date 2020-11-16
 */
@Service
@Slf4j
public class MixedDataSourceServiceImpl implements MixedDataSourceService {

	private DataSourceSupport DATA_SOURCE_SUPPORT = new DataSourceSupport();

	@Autowired
	private MixedDataSourceDAO           mixedDataSourceDAO;
	@Autowired
	private MixedDatasourceRepositoryDAO mixedDatasourceRepositoryDAO;
	@Autowired
	private CodeTemplateSupport          codeTemplateSupport;

	@Override
	public PageResult<MixedDataSourceInfoModel> queryPage(MixedDataSourceListParam param) {
		Map<String, Object> paramMapper = param.toMapper();
		log.info("page query mixed datasource. param: '{}'", JSONUtils.toJSONString(paramMapper));

		// 1. query count
		long count = mixedDataSourceDAO.selectCount(paramMapper);

		// 2. query record
		if (count != 0L) {
			List<MixedDataSourceEntity> entityList = mixedDataSourceDAO.selectPage(paramMapper, param.toPageRequest());

			if (!CollectionUtils.isEmpty(entityList)) {
				// convert result object
				List<MixedDataSourceInfoModel> result = entityList.stream().map(entity -> new MixedDataSourceInfoModel(entity)).collect(Collectors.toList());

				return new PageResult<>(result, count);
			}
		}

		return new PageResult<>(new ArrayList<>(0), count);
	}

	@Override
	public MixedDataSourceDetailModel queryDetailByMixedName(String mixedName) {
		log.info("query datasource detail by mixed name: '{}'", mixedName);

		// query datasource
		MixedDataSourceEntity mixedDataSourceEntity = mixedDataSourceDAO.selectByMixedName(mixedName);
		if (mixedDataSourceEntity == null) {
			throw new BizException(MessageEnum.MIXED_DATASOURCE_NOT_EXIST);
		}

		// convert to MixedDataSourceModel
		MixedDataSourceModel       mixedDataSourceModel = DATA_SOURCE_SUPPORT.entityToMixedDataSourceModel(mixedDataSourceEntity);
		MixedDataSourceDetailModel detailModel          = new MixedDataSourceDetailModel(mixedDataSourceModel);
		detailModel.setMixedDesc(mixedDataSourceEntity.getMixedDesc());
		detailModel.setCreateUser(mixedDataSourceEntity.getCreateUser());
		detailModel.setCreateTime(DateFormatUtils.format(mixedDataSourceEntity.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));

		String configJson = mixedDataSourceEntity.getConfigParam();
		if (StringUtils.isNotEmpty(configJson)) {
			detailModel.setApplicationList(DATA_SOURCE_SUPPORT.getConfigParamApplication(configJson));
		}

		// gen sample code: spring xml
		Map<String, Object> templateParam = new HashMap<>();
		templateParam.put("MixedDataSourceName", mixedName);
		String springXmlCode = codeTemplateSupport.genCodeStr("orm_spring_xml.ftl", templateParam, true);
		detailModel.setSpringXmlCode(springXmlCode);

		// gen sample code: spring boot properties
		String springBootPropertiesCode = codeTemplateSupport.genCodeStr("orm_springboot_properties.ftl", templateParam, true);
		detailModel.setSpringBootPropertiesCode(springBootPropertiesCode);

		return detailModel;
	}

	@Override
	public MixedDataSourceModel queryMixedDataSource(ORMGetMixedConfigParams params) {
		log.info("query mixed datasource. param: {}", params);

		// query datasource
		MixedDataSourceEntity mixedDataSourceEntity = mixedDataSourceDAO.selectByMixedName(params.getMixedName());
		if (mixedDataSourceEntity == null) {
			throw new BizException(MessageEnum.MIXED_DATASOURCE_NOT_EXIST);
		}

		// check application todo

		MixedDataSourceModel mixedDataSourceModel = DATA_SOURCE_SUPPORT.entityToMixedDataSourceModel(mixedDataSourceEntity);
		return mixedDataSourceModel;
	}

	/**
	 * DataSource操作类
	 */
	private class DataSourceSupport {
		/**
		 * MixedDataSourceEntity 转换为 MixedDataSourceModel
		 *
		 * @param dataSourceEntity
		 * @return
		 */
		MixedDataSourceModel entityToMixedDataSourceModel(MixedDataSourceEntity dataSourceEntity) {
			MixedDataSourceModel mixedDataSourceModel = new MixedDataSourceModel();
			mixedDataSourceModel.setMixedName(dataSourceEntity.getMixedName());
			mixedDataSourceModel.setState(State.convert(dataSourceEntity.getState()));
			mixedDataSourceModel.setType(DBType.convert(dataSourceEntity.getDbType()));

			// query repository and atom
			List<DataSourceRepositoryAllInfoEntity> repositoryAllInfoEntityList
					= mixedDatasourceRepositoryDAO.selectAllInfoAndAtomByDataSourceId(dataSourceEntity.getId());
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

		/**
		 * 获取config_param中application配置
		 *
		 * @param configJson
		 * @return
		 */
		List<String> getConfigParamApplication(String configJson) {
			String              key          = "application";
			Map<String, Object> configMapper = JSONUtils.json2Map(configJson);

			if (configMapper != null && configMapper.containsKey(key)) {
				return (List<String>) configMapper.get(key);
			}

			return new ArrayList<>(0);
		}
	}
}
