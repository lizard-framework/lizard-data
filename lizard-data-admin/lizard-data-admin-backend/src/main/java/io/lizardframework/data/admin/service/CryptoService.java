package io.lizardframework.data.admin.service;

/**
 * @author xueqi
 * @date 2020-10-18
 */
public interface CryptoService {

	String encrypt(String data);

	String decrypt(String data);
}
