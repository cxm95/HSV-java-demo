package sockettest;

import java.io.*;
import java.net.URL;

import util.GetUrlDownload;

/**
 * * java访问URL并下载文件 *
 * 
 * @author 13盒子
 * */
public class ResourceURLServer implements Runnable {
	public static void getURLResource(String ourputFile, String urlStr)
			throws Exception {

		FileWriter fw = new FileWriter(ourputFile);
		PrintWriter pw = new PrintWriter(fw);
		URL resourceUrl = new URL(urlStr);
		InputStream content = (InputStream) resourceUrl.getContent();
		BufferedReader in = new BufferedReader(new InputStreamReader(content));
		String line;

		while ((line = in.readLine()) != null) {
			pw.println(line);
		}
		pw.close();
		fw.close();
	}

	public void run() {
		try {
			System.out.println("begin");
			getURLResource("H:\\测试.csv",
					"http://data.phishtank.com/data/online-valid.csv");
			System.out.println("end");
			//写个注释代码就长了
			GetUrlDownload w = new GetUrlDownload();
			//w.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}