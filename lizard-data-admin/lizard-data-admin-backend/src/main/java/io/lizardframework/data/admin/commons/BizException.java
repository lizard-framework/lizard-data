package io.lizardframework.data.admin.commons;

import io.lizardframework.data.admin.message.MessageEnum;

/**
 * @author xueqi
 * @date 2020-09-25
 */
public class BizException extends RuntimeException {

	private MessageEnum messageEnum;

	public BizException(MessageEnum messageEnum) {
		super();
		this.messageEnum = messageEnum;
	}

	public MessageEnum getRespMessage() {
		return messageEnum;
	}
}
