package io.lizardframework.data.admin.service.resources.impl;

import io.lizardframework.data.admin.commons.BizException;
import io.lizardframework.data.admin.commons.pageable.PageResult;
import io.lizardframework.data.admin.controller.resourcesManager.database.params.DataBaseAddParam;
import io.lizardframework.data.admin.controller.resourcesManager.database.params.DataBaseListParam;
import io.lizardframework.data.admin.message.MessageEnum;
import io.lizardframework.data.admin.model.resources.DatabaseInfoModel;
import io.lizardframework.data.admin.repository.ResourcesDatabaseDAO;
import io.lizardframework.data.admin.repository.entity.ResourcesDatabaseEntity;
import io.lizardframework.data.admin.service.CryptoService;
import io.lizardframework.data.admin.service.resources.DatabaseResourceService;
import io.lizardframework.data.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xueqi
 * @date 2020-11-15
 */
@Service
@Slf4j
public class DatabaseResourceServiceImpl implements DatabaseResourceService {

	@Autowired
	private ResourcesDatabaseDAO resourcesDatabaseDAO;
	@Autowired
	private CryptoService        cryptoService;

	@Override
	public PageResult<DatabaseInfoModel> queryPage(DataBaseListParam param) {
		Map<String, Object> paramMapper = param.toMapper();
		log.info("page query database resources. param: '{}'", JSONUtils.toJSONString(paramMapper));

		// 1. query count
		long count = resourcesDatabaseDAO.selectCount(paramMapper);

		// 2. query record
		if (count != 0L) {
			List<ResourcesDatabaseEntity> entityList = resourcesDatabaseDAO.selectPage(paramMapper, param.toPageRequest());
			if (!CollectionUtils.isEmpty(entityList)) {
				// convert result object
				List<DatabaseInfoModel> result = entityList.stream().map(entity -> DatabaseInfoModel.build(entity, true)).collect(Collectors.toList());

				return new PageResult<>(result, count);
			}
		}

		return new PageResult<>(new ArrayList<>(0), count);
	}

	@Override
	public void save(DataBaseAddParam param) {
		ResourcesDatabaseEntity entity = new ResourcesDatabaseEntity();
		Date                    date   = new Date();

		entity.setDbType(param.getDbType());
		entity.setDbName(param.getDbName());
		entity.setDbHost(param.getDbHost());
		entity.setDbPort(param.getDbPort());
		entity.setDbUsername(cryptoService.encrypt(param.getDbUsername()));
		entity.setDbPassword(cryptoService.encrypt(param.getDbPassword()));
		entity.setCreateTime(date);
		entity.setUpdateTime(date);

		resourcesDatabaseDAO.insertSelective(entity);
	}

	@Override
	public DatabaseInfoModel queryBasicInfo(Long id) {
		log.info("query database resource basic info. id: '{}'", id);

		ResourcesDatabaseEntity entity = resourcesDatabaseDAO.selectByPrimaryKey(id);
		if (entity == null) {
			throw new BizException(MessageEnum.DATABSE_RESOURCE_NOT_EXIST);
		}

		DatabaseInfoModel model = DatabaseInfoModel.build(entity, true);
		return model;
	}

	@Override
	public DatabaseInfoModel queryAuthInfo(Long id) {
		log.info("query database resource auth info. id: '{}'", id);

		ResourcesDatabaseEntity entity = resourcesDatabaseDAO.selectByPrimaryKey(id);
		if (entity == null) {
			throw new BizException(MessageEnum.DATABSE_RESOURCE_NOT_EXIST);
		}

		DatabaseInfoModel model = DatabaseInfoModel.build(entity, false);

		// 解密结果
		String username = cryptoService.decrypt(model.getDbUsername());
		String password = cryptoService.decrypt(model.getDbPassword());
		model.decryptAuthInfo(username, password);

		return model;
	}
}
