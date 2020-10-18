package io.lizardframework.data.admin.service.impl;

import io.lizardframework.data.admin.commons.PageableResp;
import io.lizardframework.data.admin.controller.operator.params.DataBaseAddParam;
import io.lizardframework.data.admin.controller.operator.params.DataBaseListParam;
import io.lizardframework.data.admin.dao.DbInfoDAO;
import io.lizardframework.data.admin.dao.entity.DbInfoEntity;
import io.lizardframework.data.admin.model.DataBaseInfoModel;
import io.lizardframework.data.admin.service.CryptoService;
import io.lizardframework.data.admin.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xueqi
 * @date 2020-10-16
 */
@Service
public class DataBaseServiceImpl implements DataBaseService {

	@Autowired
	private CryptoService cryptoService;
	@Autowired
	private DbInfoDAO     dbInfoDAO;

	@Override
	public PageableResp<List<DataBaseInfoModel>> queryPage(DataBaseListParam param) {
		// 1. query count
		long count = dbInfoDAO.countPage(param.toMapper());

		// 2. select record
		List<DbInfoEntity> list = dbInfoDAO.selectPage(param.toMapper(), param.toPageRequest());
		if (!CollectionUtils.isEmpty(list)) {
			List<DataBaseInfoModel> resultlist = list.stream().map(entity -> new DataBaseInfoModel(entity)).collect(Collectors.toList());
			return new PageableResp<>(count, resultlist);
		}

		return new PageableResp<>(count, new ArrayList<>(0));
	}

	@Override
	public void save(DataBaseAddParam param) {
		// encode username and password
		param.setDbUsername(cryptoService.encrypt(param.getDbUsername()));
		param.setDbPassword(cryptoService.encrypt(param.getDbPassword()));

		DbInfoEntity entity = param.toEntity();
		dbInfoDAO.insert(entity);
	}
}
