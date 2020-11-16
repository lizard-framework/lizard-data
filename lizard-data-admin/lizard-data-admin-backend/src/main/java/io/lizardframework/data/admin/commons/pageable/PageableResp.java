package io.lizardframework.data.admin.commons.pageable;

import io.lizardframework.data.admin.commons.BizException;
import io.lizardframework.data.admin.commons.Resp;
import io.lizardframework.data.admin.message.MessageEnum;
import lombok.Getter;

/**
 * @author xueqi
 * @date 2020-10-16
 */
@Getter
public class PageableResp<T> extends Resp {

	private long itemsCount;

	public PageableResp(T data) {
		super(data);
	}

	public PageableResp(MessageEnum messageEnum, T data) {
		super(messageEnum, data);
	}

	public PageableResp(BizException ex) {
		super(ex);
	}

	public PageableResp(MessageEnum messageEnum) {
		super(messageEnum);
	}

	public PageableResp(long itemsCount, T data) {
		this(data);
		this.itemsCount = itemsCount;
	}
}
