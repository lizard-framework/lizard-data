package io.lizardframework.data.admin.service.impl;

import io.lizardframework.data.admin.service.CryptoService;
import io.lizardframework.data.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author xueqi
 * @date 2020-10-18
 */
@Service
public class CryptoServiceImpl implements CryptoService {

	@Value("${crypto.key}")
	private String cryptoKey;

	@Override
	public String encrypt(String data) {
		return CryptoUtils.encryptByECB(cryptoKey, data);
	}

	@Override
	public String decrypt(String data) {
		return CryptoUtils.decryptByECB(cryptoKey, data);
	}
}
