package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DeleteDao {
	/**
	 * ɾ��url
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
		JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
		pstmt.close();
		return 1;

	}
}
