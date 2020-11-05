package io.lizardframework.data.admin.controller.resourcesManager.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 资源管理 - 应用管理 page controller
 *
 * @author xueqi
 * @date 2020-11-04
 */
@Controller
@RequestMapping("/resources-manager/application-manager")
public class ApplicationManagerPageController {

	/**
	 * 应用管理主页面
	 *
	 * @return
	 */
	@GetMapping("index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("resources-manager/application/application-manager-index");
		return mav;
	}

}
