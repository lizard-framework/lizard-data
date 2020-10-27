package io.lizardframework.data.admin.support;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.io.StringWriter;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-10-27
 */
@Component
@Slf4j
public class CodeTemplateSupport {

	@Autowired
	private Configuration configuration;

	public String genCodeStr(String ftl, Map<String, Object> params, boolean htmlEscape) {
		try {
			StringWriter stringWriter = new StringWriter();
			Template     template     = configuration.getTemplate(ftl, "UTF-8");

			template.process(params, stringWriter);
			stringWriter.flush();
			if (htmlEscape) {
				return HtmlUtils.htmlEscape(stringWriter.toString());
			}

			return stringWriter.toString();
		} catch (Exception e) {
			log.error("code template support gen code str exception:", e);
			return "";
		}
	}

}
