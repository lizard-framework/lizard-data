package io.lizardframework.data.admin.dao;

import io.lizardframework.data.admin.dao.entity.OrmMixedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * @author xueqi
 * @date 2020-09-25
 */
@Component
public class OrmMixedDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public OrmMixedEntity selectByMixedName(String mixedName) {
		String sql = "select id, mixed_name as mixedName, mixed_desc as mixedDesc, state, db_type as dbType, " +
				"create_user as createUser, create_time as createTime, update_time as updateTime " +
				"from t_orm_mixed where mixed_name = ?";

		RowMapper<OrmMixedEntity> rowMapper = new BeanPropertyRowMapper<>(OrmMixedEntity.class);
		return jdbcTemplate.queryForObject(sql, rowMapper, mixedName);
	}
}
