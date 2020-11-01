package io.lizardframework.data.admin.controller.page;

import io.lizardframework.data.admin.model.ApplicationInfoModel;
import io.lizardframework.data.admin.model.OrmMixedDetailModel;
import io.lizardframework.data.admin.service.ApplicationService;
import io.lizardframework.data.admin.service.OrmMixedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author xueqi
 * @date 2020-10-19
 */
@Controller
@RequestMapping("/application-config")
public class ApplicationConfigPageController {

	@Autowired
	private OrmMixedService    ormMixedService;
	@Autowired
	private ApplicationService applicationService;

	@RequestMapping(value = "orm", method = RequestMethod.GET)
	public ModelAndView ormConfigList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("application_config/orm-config-list");

		return mav;
	}

	@RequestMapping(value = "orm/detail/{mixedName}", method = RequestMethod.GET)
	public ModelAndView ormConfigDetail(@PathVariable("mixedName") String mixedName) {
		OrmMixedDetailModel detail = ormMixedService.queryDetailByMixedName(mixedName);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("application_config/orm-config-detail");
		mav.addObject("detail", detail);

		return mav;
	}

	@RequestMapping(value = "orm/add", method = RequestMethod.GET)
	public ModelAndView ormConfigAdd() {
		List<ApplicationInfoModel> allApplicationList = applicationService.queryAll();

		ModelAndView mav = new ModelAndView();
		mav.setViewName("application_config/orm-config-add");

		mav.addObject("all_application_list", allApplicationList);
		return mav;
	}
}
