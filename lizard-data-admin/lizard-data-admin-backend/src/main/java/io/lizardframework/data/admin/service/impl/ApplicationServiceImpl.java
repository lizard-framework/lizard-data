package io.lizardframework.data.admin.service.impl;

import io.lizardframework.data.admin.commons.PageableResp;
import io.lizardframework.data.admin.controller.operator.resources.params.ApplicationAddParam;
import io.lizardframework.data.admin.controller.operator.resources.params.ApplicationListParam;
import io.lizardframework.data.admin.dao.ApplicationInfoDAO;
import io.lizardframework.data.admin.dao.entity.ApplicationInfoEntity;
import io.lizardframework.data.admin.model.ApplicationInfoModel;
import io.lizardframework.data.admin.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xueqi
 * @date 2020-10-29
 */
@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationInfoDAO applicationInfoDAO;

	@Override
	public PageableResp<List<ApplicationInfoModel>> queryPage(ApplicationListParam param) {
		// 1. query count
		long count = applicationInfoDAO.countPage(param.toMapper());

		// 2. select record
		List<ApplicationInfoEntity> list = applicationInfoDAO.selectPage(param.toMapper(), param.toPageRequest());
		if (!CollectionUtils.isEmpty(list)) {
			List<ApplicationInfoModel> resultlist = list.stream().map(entity -> new ApplicationInfoModel(entity)).collect(Collectors.toList());
			return new PageableResp<>(count, resultlist);
		}

		return new PageableResp<>(count, new ArrayList<>(0));
	}

	@Override
	public void save(ApplicationAddParam param) {
		Date                  date   = new Date();
		ApplicationInfoEntity record = new ApplicationInfoEntity();

		record.setApplicationName(param.getApplicationName());
		record.setApplicationDesc(param.getApplicationDesc());
		record.setOwnerName(param.getOwner());
		record.setCreateTime(date);
		record.setUpdateTime(date);

		applicationInfoDAO.save(record);
	}

	@Override
	public List<ApplicationInfoModel> queryAll() {
		Map<String, Object> params = new HashMap<>();

		// 查询全部记录
		List<ApplicationInfoEntity> list = applicationInfoDAO.selectPage(params, null);
		if (!CollectionUtils.isEmpty(list)) {
			List<ApplicationInfoModel> resultlist = list.stream().map(entity -> new ApplicationInfoModel(entity)).collect(Collectors.toList());
			return resultlist;
		}

		return new ArrayList<>(0);
	}
}
