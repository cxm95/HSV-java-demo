package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;


public class URLRunnable implements Runnable {
	public static int i=0;
	public static int j=0;
	
	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	@Override
	public void run() {
		//ProgressBar main=new ProgressBar();
		// TODO Auto-generated method stub
		System.out.println("begin2");
		GetUrlDownload w=new GetUrlDownload();
		   String FileName="H:/测试.csv";
	       File myFile=new File(FileName);
	       util.DbUtil dbUtil = new util.DbUtil();
	       String log = "urlblack";
	       if(!myFile.exists())
	       { 
	           System.err.println("Can't Find " + FileName);
	       }

	       try 
	       {
	           BufferedReader in = new BufferedReader(new FileReader(myFile));
	           BufferedReader in2 = new BufferedReader(new FileReader(myFile));
	           while(in2.readLine()!=null){
	        	   this.j++;
	           }
	           String str;
				// 插入数据建立连接
				String sql = "insert into " + log + " values(?,crc32(?)) ";
				// 查询数据建立连接 插入前先查询
				String sql2 = "select * from " + log
						+ " where url=? and url_crc=CRC32(?)";
				//執行語句
				PreparedStatement pstmt = dbUtil.getCon().prepareStatement(sql);
				PreparedStatement pstmt2 = dbUtil.getCon().prepareStatement(sql2);
				while ((str = in.readLine()) != null) {
					this.i++;
					str = w.geturldownload(str);
					pstmt2.setString(1, str);
					pstmt2.setString(2, str);
					if (!pstmt2.executeQuery().next()) {
						pstmt.setString(1, str);
						pstmt.setString(2, str);
						pstmt.addBatch();
						pstmt2.executeQuery().close();
					}
					str = w.geturldownload(str);
				}
				pstmt.executeBatch();
				// dbUtil.getCon().commit();
				pstmt.close();
				pstmt2.close();
				in.close();
				System.out.println("sw");

	       } 
	       catch (Exception e) 
	       {
	           e.getStackTrace();
	       }
	}
	
	

}