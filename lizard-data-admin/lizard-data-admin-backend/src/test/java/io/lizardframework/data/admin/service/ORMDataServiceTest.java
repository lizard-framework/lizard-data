package io.lizardframework.data.admin.service;

import io.lizardframework.data.orm.model.MixedDataSourceModel;
import io.lizardframework.data.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author xueqi
 * @date 2020-09-25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class ORMDataServiceTest {

	@Autowired
	private ORMDataService ormDataService;

	@Test
	public void queryMixedDataSourceTest() {
		String mixedName = "TestMixedDataSource";

		MixedDataSourceModel model = ormDataService.queryMixedDataSource(mixedName);

		System.out.println(JSONUtils.toJSONString(model));
	}
}
