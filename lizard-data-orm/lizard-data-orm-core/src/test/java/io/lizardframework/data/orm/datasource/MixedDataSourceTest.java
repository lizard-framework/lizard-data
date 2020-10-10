package io.lizardframework.data.orm.datasource;

import io.lizardframework.data.orm.AbstractSpringTest;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author xueqi
 * @date 2020-10-09
 */
public class MixedDataSourceTest extends AbstractSpringTest {

	@Test
	public void assertGetOneMixedDataSourceByName() throws SQLException {
		String beanName = "TestMixedDataSource";

		DataSource dataSource = super.getBean("applicationContext-test.xml", beanName);

		doQuery(dataSource);
	}

	@Test
	public void assertGetMultipleDataSourceByName() throws SQLException {
		DataSource ds1 = super.getBean("applicationContext-test.xml", "TestMixedDataSource");
		doQuery(ds1);

		DataSource ds2 = super.getBean("applicationContext-test.xml", "TestMixedMSDataSource");
		doQuery(ds2);
	}

	private void doQuery(DataSource dataSource) throws SQLException {
		Connection        connection = dataSource.getConnection();
		PreparedStatement ps         = connection.prepareStatement("SELECT 1 FROM dual");
		ResultSet         rs         = ps.executeQuery();
		while (rs.next()) {
			Assert.assertEquals("1", rs.getString("1"));
		}
	}
}
