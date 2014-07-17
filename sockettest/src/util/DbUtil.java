package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {

	private String dbUrl = "jdbc:mysql://localhost:3306/url";
	private String dbUserName = "root";
	private String dbPassword = "123456";
	private String jdbcName = "com.mysql.jdbc.Driver";

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection getCon() throws Exception {
		Class.forName(jdbcName);
		Connection con = DriverManager.getConnection(dbUrl, dbUserName,
				dbPassword);
		return con;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param con
	 * @throws Exception
	 */
	public void closeCon(Connection con) throws Exception {
		if (con != null) {
			con.close();
		}
	}

}
