package io.lizardframework.data.admin.commons;

import lombok.Data;
import org.springframework.data.domain.PageRequest;

/**
 * @author xueqi
 * @date 2020-10-29
 */
@Data
public class PageableParam {

	private Integer pageIndex = 1;
	private Integer pageSize  = 10;

	public PageRequest toPageRequest() {
		PageRequest pageable = PageRequest.of(pageIndex - 1, pageSize);
		return pageable;
	}
}
