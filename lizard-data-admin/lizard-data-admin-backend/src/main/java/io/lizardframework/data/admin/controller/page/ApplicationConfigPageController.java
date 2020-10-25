package io.lizardframework.data.admin.controller.page;

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

	@RequestMapping(value = "orm", method = RequestMethod.GET)
	public ModelAndView databaseConfigList() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("application_config/orm-config-list");

		return mav;
	}

	@RequestMapping(value = "orm/{id}", method = RequestMethod.GET)
	public ModelAndView databaseConfigDetail(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("application_config/orm-config-detail");

		return mav;
	}
}
