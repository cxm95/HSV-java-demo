package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DeleteDao {
	/**
	 * É¾³ýurl
	 * 
	 * @param con
	 * @param url
	 * @throws Exception
	 */
	public static int Delete(Connection con, String url, String log)
			throws Exception {
		String sql = "delete from " + log + " where url_crc=crc32('" + url
				+ "') or url='" + url + "'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.addBatch(sql);
		pstmt.executeBatch();
		JOptionPane.showMessageDialog(null, "É¾³ý³É¹¦");
		pstmt.close();
		return 1;

	}
}
