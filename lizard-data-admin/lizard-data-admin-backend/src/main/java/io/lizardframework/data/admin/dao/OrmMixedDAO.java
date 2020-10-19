package io.lizardframework.data.admin.dao;

import io.lizardframework.data.admin.dao.entity.OrmMixedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-09-25
 */
@Component
public class OrmMixedDAO {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private JdbcTemplate               jdbcTemplate;

	private static final String SELECT_ALL_SQL = "select id, mixed_name as mixedName, mixed_desc as mixedDesc, state, db_type as dbType, " +
			"create_user as createUser, create_time as createTime, update_time as updateTime " +
			"from t_orm_mixed where 1 = 1 ";

	public OrmMixedEntity selectByMixedName(String mixedName) {
		String sql = SELECT_ALL_SQL + " and mixed_name = ?";

		RowMapper<OrmMixedEntity> rowMapper = new BeanPropertyRowMapper<>(OrmMixedEntity.class);
		return jdbcTemplate.queryForObject(sql, rowMapper, mixedName);
	}

	public long countPage(Map<String, Object> params) {
		StringBuilder sqlBuilder = new StringBuilder("select count(id) from t_orm_mixed where 1=1 ");
		buildQueryParam(sqlBuilder, params);

		return namedParameterJdbcTemplate.queryForObject(sqlBuilder.toString(), params, Long.class);
	}

	public List<OrmMixedEntity> selectPage(Map<String, Object> params, Pageable pageable) {
		return null;
	}

	private void buildQueryParam(StringBuilder sqlBuilder, Map<String, Object> params) {
		if (params.containsKey("mixedName")) {
			sqlBuilder.append("and mixed_name like mixedName ");
		}
	}
}
