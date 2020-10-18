package io.lizardframework.data.admin.commons;

import io.lizardframework.data.admin.message.RespMessage;
import lombok.Getter;

/**
 * @author xueqi
 * @date 2020-09-27
 */
@Getter
public class Resp<T> {

	private String code    = RespMessage.SUCCESS.getCode();
	private String message = RespMessage.SUCCESS.getMessage();
	private T      data;

	public Resp() {
	}

	public Resp(T data) {
		this.data = data;
	}

	public Resp(RespMessage respMessage, T data) {
		this.code = respMessage.getCode();
		this.message = respMessage.getMessage();
		this.data = data;
	}

	public Resp(BizException ex) {
		this.code = ex.getRespMessage().getCode();
		this.message = ex.getRespMessage().getMessage();
	}

	public Resp(RespMessage respMessage) {
		this.code = respMessage.getCode();
		this.message = respMessage.getMessage();
	}
}
