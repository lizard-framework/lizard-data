package io.lizardframework.data.admin.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xueqi
 * @date 2020-10-16
 */
@Controller
@RequestMapping("/resources")
public class ResourcesPageController {

	/**
	 * go to database_index.ftl page
	 *
	 * @return
	 */
	@RequestMapping(value = "databases", method = RequestMethod.GET)
	public ModelAndView databaseIndex() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("resources/database_index");

		return mav;
	}
}
