package sockettest;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ui.JFrameGame;

public class Webcontent implements RecordHandler {
	private StringBuffer sb2;

	/*
	 * 读取一个网页全部内容
	 */

	public String getOneHtml(String htmlurl) throws Exception {
		URL url;
		String temp;
		StringBuffer sb = new StringBuffer();
		int i = 0;
		url = new URL(htmlurl);
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(10);
		connection.setReadTimeout(10);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream(), "utf-8"));// 读取网页内容
		while ((temp = in.readLine()) != null && i < 30) {
			// System.out.println("while in getonehtml1 for loop");
			sb.append(temp);
			sb.append("\r\n");
			i++;
		}
		in.close();
		return sb.toString();
	}

	/*
	 * 重写此方法，用于读取不同编码格式的网页全部内容
	 */
	public String getOneHtml(String htmlurl, String charset) throws Exception {
		URL url;
		String temp;
		sb2 = new StringBuffer();

		url = new URL(htmlurl);
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(10);
		connection.setReadTimeout(10);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream(), charset));// 读取网页全部内容
		while ((temp = in.readLine()) != null) {
			// System.out.println("in getonehtml2 for loop");
			sb2.append(temp);
			sb2.append("\r\n");
		}
		in.close();
		return sb2.toString();
	}

	private String getname() {
		String name = Long.toString(System.currentTimeMillis());
		return name;
	}

	/*
	 * 读取文件中需要过滤掉的关键词，并存入一个数组中
	 */
	public String[] getLineFromTxt(File file, String split) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String firstLine = br.readLine(); // 就读第一行
		String[] arrs = firstLine.split(" ");
		int[] arr = new int[arrs.length];
		if (br != null) {
			br.close();
			br = null;
		}
		return arrs;
	}

	/*
	 * 过滤关键词的方法，输入为byte数组和编码格式，输出为byte数组
	 */
	public byte[] filterword(byte srtbyte[], String charset) throws Exception {
		File file = new File("H:\\keywordtobefilted.txt");
		String[] a = getLineFromTxt(file, " ");
		String res = new String(srtbyte, charset);
		for (int i = 0; i < a.length; i++) {
			res = res.replaceAll(a[i], "河蟹河蟹河蟹河蟹");
		}
		srtbyte = res.getBytes();
		for (int s1 = 0; s1 < 10; s1++) {
			System.out.println(srtbyte[s1]);
		}
		System.out.println(res);
		return srtbyte;
	}

	/*
	 * 一个写入文件的函数，将分词写入文件
	 */
	public String getwritetxt(String s) throws IOException {
		String r = getname();
		File txt = new File("H:" + r + ".txt");
		if (!txt.exists()) {
			txt.createNewFile();
		}
		byte bytes[] = new byte[512];
		bytes = s.getBytes(); // 新加的
		int b = s.length(); // 改
		FileOutputStream fos = new FileOutputStream(txt);
		fos.write(bytes, 0, b);
		fos.close();
		return "H:" + r + ".txt";

	}

	public String getwritehtml(String s, String charset, int sign)
			throws Exception {
		String r = getname();
		File html = new File("H:" + r + ".html");
		if (!html.exists()) {
			html.createNewFile();
		}
		byte[] bytes = s.getBytes(charset); // 新加的
		int b = s.length(); // 改
		FileOutputStream fos = new FileOutputStream(html);

		if (sign == 1) {
			Webcontent c = new Webcontent();
			bytes = c.filterword(bytes, charset);
		}
		fos.write(bytes, 0, b);
		fos.close();
		return "H:" + r + ".html";

	}

	/*
	 * 提取网页信息中的title
	 */
	public String getTitle(String s) {
		String regex;
		String title = "";
		List list = new ArrayList();
		regex = "<title>.*?</title>";
		Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		for (int i = 0; i < list.size(); i++) {
			title = title + list.get(i);
		}
		return outTag(title);
	}

	/*
	 * 过滤网页源码，提取其中中文字符
	 */
	public String filterString(String str) {
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (ch[i] > '!' && ch[i] < '~' || ch[i] == '!' || ch[i] == '	'
					|| ch[i] == ',' || ch[i] == ' ' || ch[i] == '?'
					|| ch[i] == '，' || ch[i] == '、' || ch[i] == ':'
					|| ch[i] == '。') {
				ch[i] = 'a'; // 替换字符
			}

		}
		String result = new String(ch);

		return result.replaceAll("a", "");
	}

	/*
	 * 运用正则表达式，提取网页中的链接
	 */
	public List getLink(String s) {
		String regex;
		List list = new ArrayList();
		regex = "<a[^>]*href=</a>";
		Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}

	/*
	 * 运用正则表达式，提取网页中的脚本信息
	 */
	public List getScript(String s) {
		String regex;
		List list = new ArrayList();
		regex = "<script.*?</script>";

		Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}

	/*
	 * 运用正则表达式，提取网页中的css
	 */
	public List getCSS(String s) {
		String regex;
		List list = new ArrayList();
		regex = "<style>.*?</style>";
		Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}

	/*
	 * 提取网页中的编码格式
	 */
	/*
	 * 运用正则表达式，提取网页中的编码格式
	 */
	public String getcharset(String s) throws IOException {
		// System.out.println("in getcharset");
		String regex;
		String charset = "";
		String s1 = s;
		List list = new ArrayList();
		regex = "charset=.*?\"";
		Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		for (int i = 0; i < 1; i++) {
			charset = charset + list.get(i);

		}
		charset = charset.replaceAll("\"", "");

		if (charset.equals("charset=")) {
			int l = s.indexOf("charset=\"", 20);
			String k1 = s.substring(l, l + 20);
			// System.out.println(k1);
			if (k1.contains("utf-8"))
				return "utf-8";
			else if (k1.contains("gbk"))
				return "gbk";
			else if (k1.contains("gb2312"))
				return "gb2312";
		}

		else
			charset = charset.replaceAll("charset=", "");
		// System.out.println("end getcharset");
		return charset;
	}

	/*
	 * 去掉网页中的标记
	 */
	public String outTag(String s) {
		return s.replaceAll("<.*?>", "");
	}

	@Override
	public void handleRecord(Record record) throws Exception {
		System.out.println("正在WebContent模块处理记录" + record.getUrl());
		JFrameGame.Log.setText("正在WebContent模块处理记录" + record.getUrl());
		String url = record.getUrl();
		String s = getOneHtml(url);
		// System.out.println("after getonehtml");
		String s2 = getcharset(s.substring(0, 400));
		// System.out.println("#######charset:" + s2);
		String s3 = getOneHtml(url, s2);
		// System.out.println(s3);
		if (record.getResponse() == 0) {
			String str1 = filterString(s3);
			String title = getTitle(s3);
			String str2 = getwritetxt(str1);
			KeyWord keyWord = new KeyWord();
			keyWord.handleFile(str2, s2, url);
			System.out.println("记录处理完毕1");
			JFrameGame.Log.setText("处理完毕1" + record.getUrl());
		} else {
			String str2 = getwritehtml(s3, s2, record.getFliter());
			HtmlCheck check = new HtmlCheck();
			check.handleFile(str2, s2, url);
			System.out.println("记录处理完毕2");
			JFrameGame.Log.setText("处理完毕2" + record.getUrl());
		}

	}

}
