package io.lizardframework.data.admin.controller.page;

import io.lizardframework.data.admin.model.OrmMixedDetailModel;
import io.lizardframework.data.admin.service.OrmMixedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xueqi
 * @date 2020-10-19
 */
@Controller
@RequestMapping("/application-config")
public class ApplicationConfigPageController {

	@Autowired
	private OrmMixedService ormMixedService;

	@RequestMapping(value = "orm", method = RequestMethod.GET)
	public ModelAndView databaseConfigList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("application_config/orm-config-list");

		return mav;
	}

	@RequestMapping(value = "orm/{mixedName}", method = RequestMethod.GET)
	public ModelAndView databaseConfigDetail(@PathVariable("mixedName") String mixedName) {
		OrmMixedDetailModel detail = ormMixedService.queryDetailByMixedName(mixedName);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("application_config/orm-config-detail");
		mav.addObject("detail", detail);

		return mav;
	}
}
