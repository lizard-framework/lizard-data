package io.lizardframework.data.admin.dao.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * t_orm_mixed table entity
 *
 * @author xueqi
 * @date 2020-09-24
 */
@Entity
@Table(name = "t_orm_mixed")
@Data
public class OrmMixedEntity {

	@Id
	@Column(name = "id")
	private Long      id;
	@Column(name = "mixed_name")
	private String    mixedName;
	@Column(name = "mixed_desc")
	private String    mixedDesc;
	@Column(name = "state")
	private String    state;
	@Column(name = "db_type")
	private String    type;
	@Column(name = "create_user")
	private String    createUser;
	@Column(name = "create_time")
	private Timestamp createTime;
	@Column(name = "update_time")
	private Timestamp updateTime;
}
