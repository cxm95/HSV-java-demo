package sockettest;

import java.io.*;
import java.net.*;

import ui.JFrameGame;
import util.DbUtil;
import main.Main;

public class AppLauncher extends HttpServer {
	static private boolean first = true;

	public AppLauncher(Socket s) {
		super(s);
	}

	public void writeLog(int c, boolean browser) throws IOException {
		if (first)
			log.write('*');
		first = false;
		log.write(c);
		if (c == '\n')
			log.write('*');
	}

	static public void main(String args[]) {
		System.err.println("�����������������ipΪ127.0.0.1���˿�Ϊ8888\n");
		JFrameGame.Log.setText("�����������������ipΪ127.0.0.1���˿�Ϊ8888\n");

		Main main = new Main();
		DbUtil dbUtil = new DbUtil();
		try {
			dbUtil.getCon();
			System.out.println("���ݿ����ӳɹ�");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("���ݿ�����ʧ��");
		}
		// �Զ����غ�����
		ResourceURLServer r = new ResourceURLServer();
		Thread t = new Thread(r);
		t.start();
		AppLauncher.setIsrunning(false);
		AppLauncher.setKeywordfilter(false);
		AppLauncher.setAutodownload(true);
		AppLauncher.setUrlcheck(true);
		AppLauncher.log = System.out;
		AppLauncher.logging = true;

		AppLauncher.addRecordHandler(new ConsoleRecordHandler());
		AppLauncher.addRecordHandler(new SqlRecordHandler());
		AppLauncher.addRecordHandler(new Statistic());
		AppLauncher.startProxy(8888, AppLauncher.class);
	}
}