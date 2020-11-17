package io.lizardframework.data.admin.service.mixed.impl;

import io.lizardframework.data.admin.commons.pageable.PageResult;
import io.lizardframework.data.admin.controller.applicationConfig.datasource.params.MixedDataSourceListParam;
import io.lizardframework.data.admin.model.mixed.MixedDataSourceInfoModel;
import io.lizardframework.data.admin.repository.MixedDataSourceRepository;
import io.lizardframework.data.admin.repository.entity.MixedDataSourceEntity;
import io.lizardframework.data.admin.service.mixed.MixedDataSourceService;
import io.lizardframework.data.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xueqi
 * @date 2020-11-16
 */
@Service
@Slf4j
public class MixedDataSourceServiceImpl implements MixedDataSourceService {

	@Autowired
	private MixedDataSourceRepository mixedDataSourceRepo;

	@Override
	public PageResult<MixedDataSourceInfoModel> queryPage(MixedDataSourceListParam param) {
		Map<String, Object> paramMapper = param.toMapper();
		log.info("page query mixed datasource. param: '{}'", JSONUtils.toJSONString(paramMapper));

		// 1. query count
		long count = mixedDataSourceRepo.selectCount(paramMapper);

		// 2. query record
		if (count != 0L) {
			List<MixedDataSourceEntity> entityList = mixedDataSourceRepo.selectPage(paramMapper, param.toPageRequest());

			if (!CollectionUtils.isEmpty(entityList)) {
				// convert result object
				List<MixedDataSourceInfoModel> result = entityList.stream().map(entity -> new MixedDataSourceInfoModel(entity)).collect(Collectors.toList());

				return new PageResult<>(result, count);
			}
		}

		return new PageResult<>(new ArrayList<>(0), count);
	}
}
