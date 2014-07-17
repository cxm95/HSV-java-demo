package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class AddDao {
	/**
	 * 添加url
	 * 
	 * @param con
	 * @param url
	 * @throws Exception
	 */
	public static int Add(Connection con, String url, String log)
			throws Exception {
		String sql = "insert into " + log + " values('" + url + "',crc32('"
				+ url + "')) ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.addBatch(sql);
		JOptionPane.showMessageDialog(null, "添加成功");
		pstmt.executeBatch();
		pstmt.close();
		return 1;
	}
}
