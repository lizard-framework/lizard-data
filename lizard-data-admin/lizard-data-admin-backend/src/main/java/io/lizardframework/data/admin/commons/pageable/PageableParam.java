package io.lizardframework.data.admin.commons.pageable;

import lombok.Data;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * @author xueqi
 * @date 2020-10-29
 */
@Data
public abstract class PageableParam {

	private Integer pageIndex = 1;
	private Integer pageSize  = 10;

	public PageRequest toPageRequest() {
		PageRequest pageable = PageRequest.of(pageIndex - 1, pageSize);
		return pageable;
	}

	public abstract Map<String, Object> toMapper();
}
