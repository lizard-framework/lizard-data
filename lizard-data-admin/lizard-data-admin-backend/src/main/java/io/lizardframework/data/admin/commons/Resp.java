package io.lizardframework.data.admin.commons;

import io.lizardframework.data.admin.message.MessageEnum;
import lombok.Getter;

/**
 * @author xueqi
 * @date 2020-09-27
 */
@Getter
public class Resp<T> {

	private String code    = MessageEnum.SUCCESS.getCode();
	private String message = MessageEnum.SUCCESS.getMessage();
	private T      data;

	public Resp() {
	}

	public Resp(T data) {
		this.data = data;
	}

	public Resp(MessageEnum messageEnum, T data) {
		this.code = messageEnum.getCode();
		this.message = messageEnum.getMessage();
		this.data = data;
	}

	public Resp(BizException ex) {
		this.code = ex.getRespMessage().getCode();
		this.message = ex.getRespMessage().getMessage();
	}

	public Resp(MessageEnum messageEnum) {
		this.code = messageEnum.getCode();
		this.message = messageEnum.getMessage();
	}
}
