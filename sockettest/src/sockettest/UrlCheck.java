package sockettest;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

import dao.AddDao;

public class UrlCheck implements RecordHandler {
	util.DbUtil dbUtil = new util.DbUtil();

	@Override
	public void handleRecord(Record record) throws UnknownHostException,
			IOException, Exception {
		int degree = 0;
		// TODO Auto-generated method stub
		if (record.getResponse() == 2) {
			String url = record.getUrl();
			String host = record.getRecord();

			if (whoischeck(host) > 100000) {
				degree++;
				System.out.println("complete");
			}

			String regex = "^(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])$";
			Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
			Matcher ma = pa.matcher(url);
			if (ma.find()) {
				degree++;

				System.out.println("complete");
			}
			if (url.indexOf('@') != -1 || url.length() > 23 || url.length() < 7) {
				degree++;
				System.out.println("complete");
			}
			if (getSubNum(url, ".") > 4) {
				degree++;
				System.out.println("complete");
			}
			if (getSubNum(url, "//") > 4) {
				degree++;
				System.out.println("complete");
			}
			if (getSubNum(url, "http") > 1) {
				degree++;
				System.out.println("complete");
			}
			List<String> list = new ArrayList<String>();
			list.add("confirm");
			list.add("account");
			list.add("banking");
			list.add("secure");
			list.add("taobaoapi");
			list.add("webscr");
			for (int i = 0; i < 8; i++) {
				if (url.indexOf((String) list.get(i)) != -1) {
					degree++;
					System.out.println("complete");
				}
			}
			if (degree > 6) {
				AddDao.Add(dbUtil.getCon(), url, "urlblack");
				record.setResponse(0);
				System.out.println("complete");
				return;
			}
			return;
		}
	}

	public int getSubNum(String a, String b) {
		int num = 0;
		String str = a;
		int index = a.indexOf(b);
		while (index != -1) {
			num++;
			str = str.substring(index + b.length() - 1);
			index = str.indexOf(b);
		}
		return num;
	}

	public static void nodeFilterTagClass(String url, String encoding,
			Class tagclass) {
		try {
			Parser parser = new Parser();
			parser.setURL(url);
			if (null == encoding) {
				parser.setEncoding(parser.getEncoding());
			} else {
				parser.setEncoding(encoding);
			}
			// 过滤页面中的链接标签
			NodeFilter filter = new NodeClassFilter(tagclass);
			NodeList list = parser.extractAllNodesThatMatch(filter);
			for (int i = 0; i < list.size(); i++) {
				Node node = (Node) list.elementAt(i);
				System.out.println("link is :" + node.toHtml());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String nodeFilterTagName(String url, String encoding,
			String tagName) {
		try {
			Parser parser = new Parser();
			parser.setURL(url);
			if (null == encoding) {
				parser.setEncoding(parser.getEncoding());
			} else {
				parser.setEncoding(encoding);
			}
			// 过滤页面中的链接标签
			NodeFilter filter = new TagNameFilter(tagName);
			NodeList list = parser.extractAllNodesThatMatch(filter);
			for (int i = 0; i < list.size(); i++) {
				Node node = (Node) list.elementAt(i);
				System.out.println("link is :" + node.toHtml());
				return node.toHtml();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void stringFilter(String url, String encoding,
			String containStr) {
		try {
			Parser parser = new Parser();
			parser.setURL(url);
			if (null == encoding) {
				parser.setEncoding(parser.getEncoding());
			} else {
				parser.setEncoding(encoding);
			}
			// OrFilter是结合几种过滤条件的‘或’过滤器
			NodeFilter filter = new StringFilter(containStr);
			NodeList list = parser.extractAllNodesThatMatch(filter);
			for (int i = 0; i < list.size(); i++) {
				Node node = (Node) list.elementAt(i);
				System.out.println("link is :" + node.toHtml());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int whoischeck(String host) {

		String baseurl = "http://data.alexa.com/data/+wQ411en8000lA?cli=10&dat=snba&ver=7.0&cdt=alx_vw=20&wid=12206&act=00000000000&ss=1680x1050&bw=964&t=0&ttl=35371&vis=1&rq=4&url=http://";
		String url = baseurl + host;
		stringFilter(url, "UTF-8", "RANK");
		String a = nodeFilterTagName(url, "UTF-8", "REACH");
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(a);
		System.out.println(m.replaceAll("").trim());
		return Integer.parseInt(m.replaceAll("").trim());
	}
}
// 有待加入数据库操作函数调用