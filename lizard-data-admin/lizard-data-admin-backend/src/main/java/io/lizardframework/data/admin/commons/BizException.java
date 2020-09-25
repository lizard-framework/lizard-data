package io.lizardframework.data.admin.commons;

import io.lizardframework.data.admin.message.RespMessage;

/**
 * @author xueqi
 * @date 2020-09-25
 */
public class BizException extends RuntimeException {

	private RespMessage respMessage;

	public BizException(RespMessage respMessage) {
		super();
		this.respMessage = respMessage;
	}

	public RespMessage getRespMessage() {
		return respMessage;
	}
}
