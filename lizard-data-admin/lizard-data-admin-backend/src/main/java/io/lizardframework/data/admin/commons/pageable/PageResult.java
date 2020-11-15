package io.lizardframework.data.admin.commons.pageable;

import lombok.Getter;

import java.util.List;

/**
 * @author xueqi
 * @date 2020-11-15
 */
@Getter
public class PageResult<T> {

	private List<T> data;
	private Long    count;

	public PageResult(List<T> data, Long count) {
		this.data = data;
		this.count = count;
	}
}
