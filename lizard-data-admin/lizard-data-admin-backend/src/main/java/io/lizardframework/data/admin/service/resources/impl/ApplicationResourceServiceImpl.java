package io.lizardframework.data.admin.service.resources.impl;

import io.lizardframework.data.admin.commons.BizException;
import io.lizardframework.data.admin.commons.pageable.PageResult;
import io.lizardframework.data.admin.controller.resourcesManager.application.params.ApplicationAddParam;
import io.lizardframework.data.admin.controller.resourcesManager.application.params.ApplicationListParam;
import io.lizardframework.data.admin.message.MessageEnum;
import io.lizardframework.data.admin.model.resources.ApplicationInfoModel;
import io.lizardframework.data.admin.repository.ResourcesApplicationRepository;
import io.lizardframework.data.admin.repository.entity.ResourcesApplicationEntity;
import io.lizardframework.data.admin.service.resources.ApplicationResourceService;
import io.lizardframework.data.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xueqi
 * @date 2020-11-16
 */
@Service
@Slf4j
public class ApplicationResourceServiceImpl implements ApplicationResourceService {

	@Autowired
	private ResourcesApplicationRepository resourcesApplicationRepo;

	@Override
	public PageResult<ApplicationInfoModel> queryPage(ApplicationListParam param) {
		Map<String, Object> paramMapper = param.toMapper();
		log.info("page query application resources. param: '{}'", JSONUtils.toJSONString(paramMapper));

		// 1. query count
		long count = resourcesApplicationRepo.selectCount(paramMapper);

		// 2. query record
		if (count != 0L) {
			List<ResourcesApplicationEntity> entityList = resourcesApplicationRepo.selectPage(paramMapper, param.toPageRequest());

			if (!CollectionUtils.isEmpty(entityList)) {
				// convert result object
				List<ApplicationInfoModel> result = entityList.stream().map(entity -> new ApplicationInfoModel(entity)).collect(Collectors.toList());

				return new PageResult<>(result, count);
			}
		}

		return new PageResult<>(new ArrayList<>(0), count);
	}

	@Override
	public void save(ApplicationAddParam param) {
		Date                       date   = new Date();
		ResourcesApplicationEntity record = new ResourcesApplicationEntity();

		record.setApplicationName(param.getApplicationName());
		record.setApplicationDesc(param.getApplicationDesc());
		record.setOwnerName(param.getOwner());
		record.setCreateTime(date);
		record.setUpdateTime(date);

		try {
			resourcesApplicationRepo.insertSelective(record);
		} catch (DuplicateKeyException e) {
			log.warn("save application resource exist already.", e);

			throw new BizException(MessageEnum.APPLICATION_RESOURCE_EXIST_ALREADY);
		}
	}
}
