package sockettest;

import java.io.*;
import java.net.URL;

import util.GetUrlDownload;

/**
 * * java����URL�������ļ� *
 * 
 * @author 13����
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
			getURLResource("H:\\����.csv",
					"http://data.phishtank.com/data/online-valid.csv");
			System.out.println("end");
			//д��ע�ʹ���ͳ���
			GetUrlDownload w = new GetUrlDownload();
			//w.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}