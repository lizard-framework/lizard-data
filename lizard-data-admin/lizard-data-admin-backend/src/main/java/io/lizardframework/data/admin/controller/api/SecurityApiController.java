package io.lizardframework.data.admin.controller.api;

import io.lizardframework.data.admin.commons.Resp;
import io.lizardframework.data.admin.controller.model.DecryptParams;
import io.lizardframework.data.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${crypto.key}")
	private String cryptoKey;

	@RequestMapping(value = "decrypt", consumes = "application/json")
	public Resp<String> decrypt(@RequestBody DecryptParams params) {
		String result = CryptoUtils.decryptByECB(cryptoKey, params.getText());

		return new Resp<>(result);
	}

}
