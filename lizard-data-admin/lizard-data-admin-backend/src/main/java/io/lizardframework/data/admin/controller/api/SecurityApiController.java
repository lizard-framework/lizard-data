package io.lizardframework.data.admin.controller.api;

import io.lizardframework.data.admin.commons.Resp;
import io.lizardframework.data.admin.controller.model.DecryptParams;
import io.lizardframework.data.admin.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xueqi
 * @date 2020-09-24
 */
@RestController
@RequestMapping("/api/security")
public class SecurityApiController {

	@Autowired
	private CryptoService cryptoService;

	@RequestMapping(value = "decrypt", consumes = "application/json")
	public Resp<String> decrypt(@RequestBody DecryptParams params) {
		String result = cryptoService.decrypt(params.getText());

		return new Resp<>(result);
	}

}
