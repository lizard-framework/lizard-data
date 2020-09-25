package io.lizardframework.data.admin.dao;

import io.lizardframework.data.admin.dao.entity.OrmMixedEntity;
import io.lizardframework.data.admin.dao.entity.OrmRepositoryAllInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xueqi
 * @date 2020-09-25
 */
@Component
public class OrmRepositoryDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * query repository all info
	 *
	 * @param mixedDataSourceId
	 * @return
	 */
	public List<OrmRepositoryAllInfoEntity> selectRepositoryAllInfo(Long mixedDataSourceId) {
		String sql = "SELECT repo.repository_name as repositoryName, repo.state as repositoryState, repo.load_balance as loadBalance, " +
				"atom.atom_name as atomName, atom.master_slave_type as masterSlaveType, atom.state as atomState, atom.weight as weight, " +
				"atom.datasource_pool_type as dataSourcePoolType, atom.jdbc_params as jdbcParams, atom.pool_config as poolConfig, " +
				"db.db_host as `host`, db.db_name as `database`, db.db_port as `port`, db.db_username as `username`, db.db_password as `password` " +
				"FROM t_orm_repository as repo, t_orm_atom as atom, t_db_info as db " +
				"WHERE repo.mixed_id = ? AND atom.repository_id = repo.id AND atom.db_info_id = db.id";

		RowMapper<OrmRepositoryAllInfoEntity> rowMapper = new BeanPropertyRowMapper<>(OrmRepositoryAllInfoEntity.class);
		return jdbcTemplate.query(sql, rowMapper, mixedDataSourceId);
	}

}
