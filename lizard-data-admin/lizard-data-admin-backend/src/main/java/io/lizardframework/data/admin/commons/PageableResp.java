package io.lizardframework.data.admin.commons;

import io.lizardframework.data.admin.message.RespMessage;
import lombok.Getter;

/**
 * @author xueqi
 * @date 2020-10-16
 */
@Getter
public class PageableResp<T> extends Resp {

	private long itemsCount;
	private T    dataResult;

	public PageableResp(T data) {
		super(data);
	}

	public PageableResp(RespMessage respMessage, T data) {
		super(respMessage, data);
	}

	public PageableResp(BizException ex) {
		super(ex);
	}

	public PageableResp(RespMessage respMessage) {
		super(respMessage);
	}

	public PageableResp(long itemsCount, T data) {
		this(data);
		this.dataResult = data;
		this.itemsCount = itemsCount;
	}
}
