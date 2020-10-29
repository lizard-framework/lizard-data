package io.lizardframework.data.admin.dao;

import io.lizardframework.data.admin.dao.entity.ApplicationInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-10-29
 */
@Component
@Slf4j
public class ApplicationInfoDAO {

	private static final String SELECT_ALL_SQL = "select id as id, application_name as applicationName, application_desc as applicationDesc, " +
			"owner_name as ownerName, create_time as createTime, update_time as updateTime from t_application_info where 1=1 ";

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private JdbcTemplate               jdbcTemplate;

	public long countPage(Map<String, Object> params) {
		StringBuilder sqlBuilder = new StringBuilder("select count(id) from t_application_info where 1=1 ");
		buildQueryParam(sqlBuilder, params);

		return namedParameterJdbcTemplate.queryForObject(sqlBuilder.toString(), params, Long.class);
	}

	public List<ApplicationInfoEntity> selectPage(Map<String, Object> params, Pageable pageable) {
		StringBuilder sqlBuilder = new StringBuilder(SELECT_ALL_SQL);
		buildQueryParam(sqlBuilder, params);

		// add page param
		if (pageable.getOffset() >= 0 && pageable.getPageSize() > 0) {
			sqlBuilder.append(" limit :offset, :pageSize");
			params.put("offset", pageable.getOffset());
			params.put("pageSize", pageable.getPageSize());
		}

		return namedParameterJdbcTemplate.query(sqlBuilder.toString(), params, new BeanPropertyRowMapper<>(ApplicationInfoEntity.class));
	}

	private void buildQueryParam(StringBuilder sqlBuilder, Map<String, Object> params) {
		if (params.containsKey("applicationName")) {
			sqlBuilder.append("and application_name like :applicationName ");
		}
	}
}
