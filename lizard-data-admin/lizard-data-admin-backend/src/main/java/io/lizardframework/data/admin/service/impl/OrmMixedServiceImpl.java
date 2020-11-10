package io.lizardframework.data.admin.service.impl;

import io.lizardframework.data.admin.commons.BizException;
import io.lizardframework.data.admin.commons.PageableResp;
import io.lizardframework.data.admin.controller.model.ORMGetMixedConfigParams;
import io.lizardframework.data.admin.controller.applicationConfig.datasource.params.OrmMixedListParam;
import io.lizardframework.data.admin.dao.OrmMixedDAO;
import io.lizardframework.data.admin.dao.OrmRepositoryDAO;
import io.lizardframework.data.admin.dao.entity.OrmMixedEntity;
import io.lizardframework.data.admin.dao.entity.OrmRepositoryAllInfoEntity;
import io.lizardframework.data.admin.message.RespMessage;
import io.lizardframework.data.admin.model.OrmMixedDetailModel;
import io.lizardframework.data.admin.model.OrmMixedInfoModel;
import io.lizardframework.data.admin.service.OrmMixedService;
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
 * @date 2020-09-25
 */
@Service
@Slf4j
public class OrmMixedServiceImpl implements OrmMixedService {

	@Autowired
	private OrmMixedDAO         ormMixedDAO;
	@Autowired
	private OrmRepositoryDAO    ormRepositoryDAO;
	@Autowired
	private CodeTemplateSupport codeTemplateSupport;


	@Override
	public MixedDataSourceModel queryMixedDataSource(ORMGetMixedConfigParams params) {
		// query mixed datasource
		OrmMixedEntity mixedEntity = ormMixedDAO.selectByMixedName(params.getMixedName());
		if (mixedEntity == null) {
			throw new BizException(RespMessage.ORM_MIXED_DATASOURCE_NOT_EXIST);
		}

		// check application todo

		return this.entityToDataSourceModel(mixedEntity);
	}

	@Override
	public PageableResp<List<OrmMixedInfoModel>> queryPage(OrmMixedListParam param) {
		// 1. query count
		long count = ormMixedDAO.countPage(param.toMapper());

		// 2. select record
		List<OrmMixedEntity> list = ormMixedDAO.selectPage(param.toMapper(), param.toPageRequest());
		if (!CollectionUtils.isEmpty(list)) {
			List<OrmMixedInfoModel> resultlist = list.stream().map(entity -> new OrmMixedInfoModel(entity)).collect(Collectors.toList());
			return new PageableResp<>(count, resultlist);
		}

		return new PageableResp<>(count, new ArrayList<>(0));
	}

	@Override
	public OrmMixedDetailModel queryDetailByMixedName(String mixedName) {
		// query mixed datasource
		OrmMixedEntity mixedEntity = ormMixedDAO.selectByMixedName(mixedName);
		if (mixedEntity == null) {
			throw new BizException(RespMessage.ORM_MIXED_DATASOURCE_NOT_EXIST);
		}

		MixedDataSourceModel mixedDataSourceModel = this.entityToDataSourceModel(mixedEntity);
		OrmMixedDetailModel  detailModel          = new OrmMixedDetailModel(mixedDataSourceModel);
		detailModel.setMixedDesc(mixedEntity.getMixedDesc());
		detailModel.setCreateUser(mixedEntity.getCreateUser());
		detailModel.setCreateTime(DateFormatUtils.format(mixedEntity.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));

		String configJson = mixedEntity.getConfigParam();
		if (StringUtils.isNotEmpty(configJson)) {
			detailModel.setApplicationList(this.getConfigParamApplication(configJson));
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

	private MixedDataSourceModel entityToDataSourceModel(OrmMixedEntity mixedEntity) {
		MixedDataSourceModel mixedDataSourceModel = new MixedDataSourceModel();
		mixedDataSourceModel.setMixedName(mixedEntity.getMixedName());
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

	/**
	 * 获取config_param中的application配置
	 *
	 * @param configJson
	 * @return
	 */
	private List<String> getConfigParamApplication(String configJson) {
		String              key          = "application";
		Map<String, Object> configMapper = JSONUtils.json2Map(configJson);

		if (configMapper != null && configMapper.containsKey(key)) {
			return (List<String>) configMapper.get(key);
		}

		return new ArrayList<>(0);
	}
}
