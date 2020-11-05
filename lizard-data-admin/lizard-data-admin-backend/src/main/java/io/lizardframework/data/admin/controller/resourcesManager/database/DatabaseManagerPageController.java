package io.lizardframework.data.admin.controller.resourcesManager.database;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 资源管理 - 数据库管理 page controller
 *
 * @author xueqi
 * @date 2020-11-04
 */
@Controller
@RequestMapping("/resources-manager/database-manager")
public class DatabaseManagerPageController {

	/**
	 * 数据库管理主页
	 *
	 * @return
	 */
	@GetMapping("index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("resources-manager/database/database-manager-index");

		return mav;
	}

}
