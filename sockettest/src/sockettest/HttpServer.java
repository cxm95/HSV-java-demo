package sockettest;

import java.net.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import ui.JFrameGame;

public class HttpServer extends Thread implements Runnable {

	// һЩ����罻���ı�־λ
	static private boolean isrunning;
	static private boolean keywordfilter;// �ؼ��ʹ��˿���
	static private boolean autodownload;
	static private boolean urlcheck;

	// ������볣��
	static public int CONNECT_RETRIES = 5;
	static public int CONNECT_PAUSE = 5;
	static public int TIMEOUT = 10;
	static public int BUFSIZ = 1024;
	static public boolean logging = false;
	static public OutputStream log = null;

	// ���������õ�Socket
	protected Socket socket;
	public static List<RecordHandler> recordHandlers = new ArrayList<RecordHandler>();

	public static boolean isUrlcheck() {
		return urlcheck;
	}

	public static void setUrlcheck(boolean urlcheck) {
		HttpServer.urlcheck = urlcheck;
	}

	public static boolean getAutodownload() {
		return autodownload;
	}

	public static void setAutodownload(boolean autodownload) {
		HttpServer.autodownload = autodownload;
	}

	public static boolean getKeywordfilter() {
		return keywordfilter;
	}

	public static void setKeywordfilter(boolean keywordfilter) {
		HttpServer.keywordfilter = keywordfilter;
	}

	// �Ƿ����еı�־λ����
	public boolean getIsrunning() {
		return isrunning;
	}

	public static void setIsrunning(boolean sign) {
		isrunning = sign;
	}

	// �ڸ���Socket�ϴ���һ�������̡߳�
	public HttpServer(Socket s) {
		socket = s;
		start();
	}

	public void writeLog(int c, boolean browser) throws IOException {
		log.write(c);
	}

	public void writeLog(byte[] bytes, int offset, int len, boolean browser)
			throws IOException {
		for (int i = 0; i < len; i++)
			writeLog((int) bytes[offset + i], browser);
	}

	// Ĭ������£���־��Ϣ�����
	// ��׼����豸
	// ��������Ը�����
	public String processHostName(String url, String host, int port, Socket sock) {
		java.text.DateFormat cal = java.text.DateFormat.getDateTimeInstance();
		System.out.println(cal.format(new java.util.Date()) + " - " + url + " "
				+ sock.getInetAddress() + "\n");
		JFrameGame.Log.setText(cal.format(new java.util.Date()) + " - " + url
				+ " " + sock.getInetAddress());
		return host;
	}

	// ִ�в������߳�
	public void run() {
		// ��ʼ״̬��������
		if (isrunning == false)
			return;

		String line;
		String host;
		int port = 80;
		Socket outbound = null;
		try {
			socket.setSoTimeout(TIMEOUT);
			InputStream is = socket.getInputStream();
			OutputStream os = null;
			try {
				// ��ȡ�����е�����
				line = "";
				host = "";
				int state = 0;
				boolean space;
				while (true) {
					int c = is.read();
					if (c == -1)
						break;
					if (logging)
						writeLog(c, true);
					space = Character.isWhitespace((char) c);
					switch (state) {
					case 0:
						if (space)
							continue;
						state = 1;
					case 1:
						if (space) {
							state = 2;
							continue;
						}
						line = line + (char) c;
						break;
					case 2:
						if (space)
							continue; // ��������հ��ַ�
						state = 3;
					case 3:
						if (space) {
							state = 4;
							// ֻȡ���������Ʋ���
							String host0 = host;
							int n;
							n = host.indexOf("//");
							if (n != -1)
								host = host.substring(n + 2);
							n = host.indexOf('/');
							if (n != -1)
								host = host.substring(0, n);
							// �������ܴ��ڵĶ˿ں�
							n = host.indexOf(":");
							if (n != -1) {
								port = Integer.parseInt(host.substring(n + 1));
								host = host.substring(0, n);
							}
							host = processHostName(host0, host, port, socket);

							Record record = new Record();
							record.setResponse(1);// ��ʼֵ�����������handle֮���response��ȷ��
							if (keywordfilter == true) {
								record.setFliter(1);
							} else {
								record.setFliter(0);
							}
							record.setRecord(host);
							record.setUrl(host0);
							System.out
									.println("XXXXXXXXXXXXXXXXhost0:" + host0);
							record.setVisitDate(new Date(System
									.currentTimeMillis()));
							System.out.println("start of notifyrecordhandlers");
							notifyRecordHandlers(record);
							System.out.println("end of notifyrecordhandlers");
							if (record.getResponse() == 0) {
								// ���ͨ����Ӧ����ô����
								// �߼����ܣ�������ȡ�ؼ���Ϊ�����ṩ����������
								record.setUrl(host0);
								Webcontent webcontent = new Webcontent();
								webcontent.handleRecord(record);
								// ������ҳ
								os = socket.getOutputStream();
								PrintWriter printWriter = new PrintWriter(os,
										true);
								FileReader fr = new FileReader(
										".\\phishing\\phishing.html");
								// ���Ի��ɹ���Ŀ¼�µ������ı��ļ�
								BufferedReader br = new BufferedReader(fr);
								String s;
								while ((s = br.readLine()) != null) {
									printWriter.write(s);
									System.out.println(s);
								}
								br.close();
								printWriter.close();
								System.out.println("phishing website!");
								continue;
							}

							// client����

							int retry = CONNECT_RETRIES;
							while (retry-- != 0) {
								try {
									outbound = new Socket(host, port);
									break;
								} catch (Exception e) {
								}
								// �ȴ�
								Thread.sleep(CONNECT_PAUSE);
							}
							if (outbound == null)
								break;
							outbound.setSoTimeout(TIMEOUT);
							os = outbound.getOutputStream();
							os.write(line.getBytes());
							os.write(' ');
							os.write(host0.getBytes());
							os.write(' ');
							System.out.println("start of pipe");
							pipe(is, outbound.getInputStream(), os,
									socket.getOutputStream());
							System.out.println("end of pipe");
							if (record.getResponse() == 2) {
								if (autodownload == true) {
									// ��������ģ�飬��������
									System.out.println("start of webcontent");
									Webcontent webcontent = new Webcontent();
									webcontent.handleRecord(record);
									System.out.println("end of webcontent");
								}
								if (urlcheck == true) {
									// ���url�Ƿ�Ϊ������վ���������������
									UrlCheck urlCheck = new UrlCheck();
									urlCheck.handleRecord(record);
								}
							}
							break;

						}
						host = host + (char) c;
						break;
					}
				}
			} catch (IOException e) {
			}

		} catch (Exception e) {
		} finally {
			try {
				socket.close();
			} catch (Exception e1) {
			}
			try {
				outbound.close();
			} catch (Exception e2) {
			}
		}
	}

	void pipe(InputStream is0, InputStream is1, OutputStream os0,
			OutputStream os1) throws IOException {
		try {
			int ir;
			byte bytes[] = new byte[BUFSIZ];
			while (true) {
				try {
					if ((ir = is0.read(bytes)) > 0) {
						os0.write(bytes, 0, ir);
						if (logging)
							writeLog(bytes, 0, ir, true);
					} else if (ir < 0)
						break;
				} catch (InterruptedIOException e) {
				}
				try {
					if ((ir = is1.read(bytes)) > 0) {
						os1.write(bytes, 0, ir);
						if (logging)
							writeLog(bytes, 0, ir, false);
					} else if (ir < 0)
						break;
				} catch (InterruptedIOException e) {
				}
			}
		} catch (Exception e0) {
			System.out.println("Pipe�쳣: " + e0);
		}
	}

	static public void startProxy(int port, Class clobj) {
		ServerSocket ssock;
		Socket sock;
		try {
			ssock = new ServerSocket(port);
			while (true) {
				Class[] sarg = new Class[1];
				Object[] arg = new Object[1];
				sarg[0] = Socket.class;
				try {
					java.lang.reflect.Constructor cons = clobj
							.getDeclaredConstructor(sarg);
					arg[0] = ssock.accept();
					cons.newInstance(arg); // ����HttpProxy�����������ʵ��
				} catch (Exception e) {
					Socket esock = (Socket) arg[0];
					try {
						esock.close();
					} catch (Exception ec) {
						System.out.println("error1!");
					}
				}
			}
		} catch (IOException e) {
			System.out.println("error2!");
		}
	}

	private void notifyRecordHandlers(Record record) throws Exception {
		for (RecordHandler recordHandler : this.recordHandlers) {
			recordHandler.handleRecord(record);
		}
	}

	public static void addRecordHandler(RecordHandler recordHandler) {
		recordHandlers.add(recordHandler);
	}

}
