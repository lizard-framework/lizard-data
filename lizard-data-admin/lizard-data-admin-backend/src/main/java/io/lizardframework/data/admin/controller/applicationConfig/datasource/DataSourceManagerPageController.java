package io.lizardframework.data.admin.controller.applicationConfig.datasource;

import io.lizardframework.data.admin.model.mixed.MixedDataSourceDetailModel;
import io.lizardframework.data.admin.service.mixed.MixedDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.ArrayList;

/**
 * 应用配置 - 数据源配置 page controller
 *
 * @author xueqi
 * @date 2020-11-05
 */
@Controller
@RequestMapping("/application-config/datasource")
public class DataSourceManagerPageController {

	@Autowired
	private MixedDataSourceService mixedDataSourceService;

	/**
	 * 数据源配置首页
	 *
	 * @return
	 */
	@GetMapping("index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("application-config/datasource/datasource-config-index");

		return mav;
	}

	/**
	 * 数据源详情页面
	 *
	 * @param mixedDataSourceName
	 * @return <ul>
	 * <li>detail: 数据源详情对象</li>
	 * </ul>
	 */
	@GetMapping("detail/{mixedDataSourceName}")
	public ModelAndView detail(@PathVariable("mixedDataSourceName") String mixedDataSourceName) {
		MixedDataSourceDetailModel detail = mixedDataSourceService.queryDetailByMixedName(mixedDataSourceName);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("application-config/datasource/datasource-config-detail");
		mav.addObject("detail", detail);

		return mav;
	}

	/**
	 * 数据源新增页面
	 *
	 * @return <ul>
	 * <li>reference_id: 添加批次标记</li>
	 * <li>all_application_list: 所有应用列表</li>
	 * </ul>
	 */
	@GetMapping("addition")
	public ModelAndView addition() {


		ModelAndView mav = new ModelAndView();
		mav.setViewName("application-config/datasource/datasource-config-addition");

		// 写入reference_id，作为一次添加的标记
		mav.addObject("reference_id", Instant.now().toEpochMilli() + "");
		mav.addObject("all_application_list", new ArrayList<>(0));
		return mav;
	}
}
