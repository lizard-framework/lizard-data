package io.lizardframework.data.admin.model;

import io.lizardframework.data.admin.dao.entity.OrmMixedEntity;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author xueqi
 * @date 2020-10-20
 */
@Data
public class OrmMixedInfoModel {
	private Long   id;
	private String mixedName;
	private String mixedDesc;
	private String state;
	private String dbType;
	private String createUser;
	private String createTime;


	public OrmMixedInfoModel(OrmMixedEntity entity) {
		this.id = entity.getId();
		this.mixedName = entity.getMixedName();
		this.mixedDesc = entity.getMixedDesc();
		this.state = entity.getState();
		this.dbType = entity.getDbType();
		this.createUser = entity.getCreateUser();
		this.createTime = DateFormatUtils.format(entity.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
	}
}
