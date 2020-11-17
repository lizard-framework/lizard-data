package io.lizardframework.data.admin.model.mixed;

import io.lizardframework.data.admin.repository.entity.MixedDataSourceEntity;
import io.lizardframework.data.enums.State;
import io.lizardframework.data.orm.enums.DBType;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author xueqi
 * @date 2020-10-20
 */
@Data
public class MixedDataSourceInfoModel {
	private Long   id;
	private String mixedName;
	private String mixedDesc;
	private State  state;
	private DBType dbType;
	private String createUser;
	private String createTime;


	public MixedDataSourceInfoModel(MixedDataSourceEntity entity) {
		this.id = entity.getId();
		this.mixedName = entity.getMixedName();
		this.mixedDesc = entity.getMixedDesc();
		this.state = State.convert(entity.getState());
		this.dbType = DBType.convert(entity.getDbType());
		this.createUser = entity.getCreateUser();
		this.createTime = DateFormatUtils.format(entity.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
	}
}
