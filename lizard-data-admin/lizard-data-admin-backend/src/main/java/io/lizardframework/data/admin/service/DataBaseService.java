package io.lizardframework.data.admin.service;

import io.lizardframework.data.admin.commons.PageableResp;
import io.lizardframework.data.admin.controller.operator.params.DataBaseListParam;
import io.lizardframework.data.admin.model.DataBaseInfoModel;

import java.util.List;

/**
 * @author xueqi
 * @date 2020-10-16
 */
public interface DataBaseService {

	PageableResp<List<DataBaseInfoModel>> queryPage(DataBaseListParam param);

}
