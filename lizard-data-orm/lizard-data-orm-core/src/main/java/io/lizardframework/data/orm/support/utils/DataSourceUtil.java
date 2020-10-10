package io.lizardframework.data.orm.support.utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author xueqi
 * @date 2020-10-10
 */
public class DataSourceUtil {

	private static final String VALID_SQL = "SELECT 1 FROM dual";

	public static void validateConnection(DataSource dataSource) throws Exception {
		Connection        connection = null;
		PreparedStatement ps         = null;

		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(VALID_SQL);

			ps.executeQuery();
		} catch (Exception e) {
			throw e;
		} finally {
			close(ps, connection);
		}
	}

	private static void close(Statement statement, Connection connection) throws Exception {
		try {
			if (statement != null)
				statement.close();
		} finally {
			if (connection != null)
				connection.close();
		}
	}

}
