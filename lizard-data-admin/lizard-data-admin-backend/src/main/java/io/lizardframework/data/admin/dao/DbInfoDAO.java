package io.lizardframework.data.admin.dao;

import io.lizardframework.data.admin.dao.entity.DbInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-10-16
 */
@Component
public class DbInfoDAO {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private JdbcTemplate               jdbcTemplate;

	private static final String SELECT_ALL_SQL = "select id as id, db_type as dbType, db_name as dbName, db_host as dbHost, " +
			"db_port as dbPort, db_username as dbUsername, db_password as dbPassword, create_time as createTime, update_time as updateTime " +
			"from t_db_info where 1=1 ";

	public long insert(DbInfoEntity entity) {
		String sql = "insert into t_db_info(db_type, db_name, db_host, db_port, " +
				"db_username, db_password, create_time, update_time) " +
				"values(?, ?, ?, ?, " +
				"?, ?, ?, ?)";

		return jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				int index = 0;

				ps.setString(++index, entity.getDbType());
				ps.setString(++index, entity.getDbName());
				ps.setString(++index, entity.getDbHost());
				ps.setInt(++index, entity.getDbPort());
				ps.setString(++index, entity.getDbUsername());
				ps.setString(++index, entity.getDbPassword());
				ps.setTimestamp(++index, new Timestamp(entity.getCreateTime().getTime()));
				ps.setTimestamp(++index, new Timestamp(entity.getUpdateTime().getTime()));
			}
		});
	}

	public DbInfoEntity selectById(Long id) {
		String sql = SELECT_ALL_SQL + " and id=?";

		return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(DbInfoEntity.class));
	}

	/**
	 * select record count
	 *
	 * @param params
	 * @return
	 */
	public long countPage(Map<String, Object> params) {
		StringBuilder sqlBuilder = new StringBuilder("select count(id) from t_db_info where 1=1 ");
		buildQueryParam(sqlBuilder, params);

		return namedParameterJdbcTemplate.queryForObject(sqlBuilder.toString(), params, Long.class);
	}

	/**
	 * select page
	 *
	 * @param params
	 * @param pageable
	 * @return
	 */
	public List<DbInfoEntity> selectPage(Map<String, Object> params, Pageable pageable) {
		StringBuilder sqlBuilder = new StringBuilder(SELECT_ALL_SQL);
		buildQueryParam(sqlBuilder, params);

		// add page param
		if (pageable.getOffset() >= 0 && pageable.getPageSize() > 0) {
			sqlBuilder.append(" limit :offset, :pageSize");
			params.put("offset", pageable.getOffset());
			params.put("pageSize", pageable.getPageSize());
		}

		return namedParameterJdbcTemplate.query(sqlBuilder.toString(), params, new BeanPropertyRowMapper<>(DbInfoEntity.class));
	}

	private void buildQueryParam(StringBuilder sqlBuilder, Map<String, Object> params) {
		if (params.containsKey("db_type")) {
			sqlBuilder.append("and db_type = :db_type ");
		}
		if (params.containsKey("db_name")) {
			sqlBuilder.append("and db_name = :db_name ");
		}
	}
}
