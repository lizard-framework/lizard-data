package io.lizardframework.data.admin.support.configuration;

import freemarker.cache.FileTemplateLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * @author xueqi
 * @date 2020-10-27
 */
@Configuration
public class FreeMarkerCodeTemplateConfiguration {

	@Bean
	public FileTemplateLoader fileTemplateLoader() throws IOException {
		FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(
				new File(this.getClass()
						.getClassLoader()
						.getResource("codeTemplates/")
						.getPath()));
		return fileTemplateLoader;
	}

	@Bean
	public freemarker.template.Configuration configuration(FileTemplateLoader fileTemplateLoader) {
		freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_30);
		configuration.setTemplateLoader(fileTemplateLoader);

		return configuration;
	}
}
