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
		String beanName = "TestMixedMSDataSource";

		DataSource dataSource = super.getBean("applicationContext-test.xml", beanName);

		Connection        connection = dataSource.getConnection();
		PreparedStatement ps         = connection.prepareStatement("SELECT 1 FROM dual");
		ResultSet         rs         = ps.executeQuery();
		while (rs.next()) {
			Assert.assertEquals("1", rs.getString("1"));
		}
	}

}
