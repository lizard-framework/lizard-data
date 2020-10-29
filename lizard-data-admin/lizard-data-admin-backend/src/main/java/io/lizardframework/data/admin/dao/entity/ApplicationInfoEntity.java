package io.lizardframework.data.admin.dao.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author xueqi
 * @date 2020-10-29
 */
@Data
public class ApplicationInfoEntity {

	private Long   id;
	private String applicationName;
	private String applicationDesc;
	private String ownerName;
	private Date   createTime;
	private Date   updateTime;
}
