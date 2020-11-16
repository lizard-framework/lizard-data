package io.lizardframework.data.admin.model;

import io.lizardframework.data.admin.repository.entity.ResourcesApplicationEntity;
import lombok.Getter;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author xueqi
 * @date 2020-10-29
 */
@Getter
public class ApplicationInfoModel {

	private Long   id;
	private String applicationName;
	private String applicationDesc;
	private String ownerName;
	private String createTime;
	private String updateTime;

	public ApplicationInfoModel(ResourcesApplicationEntity entity) {
		this.id = entity.getId();
		this.applicationName = entity.getApplicationName();
		this.applicationDesc = entity.getApplicationDesc();
		this.ownerName = entity.getOwnerName();
		this.createTime = DateFormatUtils.format(entity.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
		this.updateTime = DateFormatUtils.format(entity.getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
	}
}
