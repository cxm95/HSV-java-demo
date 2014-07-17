package sockettest;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dao.AddDao;

public class HtmlCheck implements FileHandler {
	util.DbUtil dbUtil = new util.DbUtil();

	@Override
	public void handleFile(String filename, String charset, String url)
			throws Exception {
		// ���Ȱ���charset�ĸ�ʽ�����ļ���Ȼ�������з�������
		String log = "urlblack";
		int degree = 0;
		File input = new File(filename);
		Document doc = Jsoup.parse(input, charset, url);
		Elements hrefs = doc.getElementsByClass("icp");
		if (hrefs.size() != 0) {
			// ���Ϊ0�����жϣ������Ϊ0�����ж��Ƿ�Ϊ�����ӣ�����ǿ����ӵĻ���Ϊ������վ
			for (Element link : hrefs) {
				String icp = link.attr("href");
				if (icp == null) {
					// ȷ��Ϊ������վ��ֹ����
					degree++;
				}
			}
			// ȷ��Ϊ�ǵ�����վ��ֹ����
			return;
		}
		if (formjudge(doc) == false) {
			// ���ǵ�����վ���˳��ж�
			return;
		}
		if (indentfyjudge(doc, url) == false) {
			degree++;
		}
		if (voidconnection(doc) == false) {
			degree++;
		}
		if (externalrequest(doc, url) == false) {
			degree++;
		}
		if (degree > 3) {
			AddDao.Add(dbUtil.getCon(), url, log);
		}
		return;
	}

	private boolean formjudge(Document doc) {
		// ��Ե�����վ͵ȡ�û����������Σ�����������ж����޴˷��湦�ܣ������Ч��
		Elements form = doc.select("form");
		if (form.size() > 0) {
			return true;
		}
		return false;
	}

	private boolean externalrequest(Document doc, String url) {
		// �ⲿ����

		String regex = "(http://|https://)?([^/]*)(/?.*)";
		ArrayList<String> list = new ArrayList<String>();
		Elements img = doc.select("img");
		Elements scr = doc.select("script");
		Elements fra = doc.select("frame");
		Elements ifra = doc.select("iframe");
		Elements in = doc.select("input");
		/*
		 * System.out.println(img); System.out.println(scr);
		 * System.out.println(fra); System.out.println(ifra);
		 * System.out.println(in);
		 */
		for (Element link : img) {
			String imagesPath = link.attr("src");
			String temp = imagesPath.replaceAll(regex, "$2");
			// System.out.println("��ǰ����·��:"+imagesPath);
			int start = temp.indexOf(".");
			int end = temp.indexOf(".", start + 1);
			if (end > start)
				list.add(temp.substring(start + 1, end));
		}
		for (Element link : scr) {
			String imagesPath = link.attr("src");
			String temp = imagesPath.replaceAll(regex, "$2");
			// System.out.println("��ǰ����·��:"+imagesPath);
			int start = temp.indexOf(".");
			int end = temp.indexOf(".", start + 1);
			if (end > start)
				list.add(temp.substring(start + 1, end));
		}
		for (Element link : fra) {
			String imagesPath = link.attr("src");
			String temp = imagesPath.replaceAll(regex, "$2");
			// System.out.println("��ǰ����·��:"+imagesPath);
			int start = temp.indexOf(".");
			int end = temp.indexOf(".", start + 1);
			if (end > start)
				list.add(temp.substring(start + 1, end));
		}
		for (Element link : ifra) {
			String imagesPath = link.attr("src");
			String temp = imagesPath.replaceAll(regex, "$2");
			// System.out.println("��ǰ����·��:"+imagesPath);
			int start = temp.indexOf(".");
			int end = temp.indexOf(".", start + 1);
			if (end > start)
				list.add(temp.substring(start + 1, end));
		}
		for (Element link : in) {
			String imagesPath = link.attr("src");
			String temp = imagesPath.replaceAll(regex, "$2");
			// System.out.println("��ǰ����·��:"+imagesPath);
			int start = temp.indexOf(".");
			int end = temp.indexOf(".", start + 1);
			if (end > start)
				list.add(temp.substring(start + 1, end));
		}
		int mf = 0, f = 0;
		String identfy = "";
		Set<String> uniqueSet = new HashSet<String>(list);
		for (String temp : uniqueSet) {
			f = Collections.frequency(list, temp);
			if (f > mf) {
				mf = f;
				identfy = temp;
			}
		}
		System.out.println("indentfy:" + identfy);
		int ustart = url.indexOf(".");
		int uend = url.indexOf(".", ustart + 1);
		String uidentfy = "";
		if (uend > ustart) {
			uidentfy = url.substring(ustart, uend);
		}
		// ��ҳ��������ҳURL��ݵ�һ����
		if (!uidentfy.equals(identfy)) {
			return false;
		}
		return true;

	}

	private boolean voidconnection(Document doc) {
		// ������
		Elements hrefs = doc.select("a[href]");
		Elements jshrefs = hrefs.select("[href^=javascript]");
		Elements voidhrefs = hrefs.select("[href^=#]");
		if ((int) (hrefs.size() / jshrefs.size() + voidhrefs.size()) > 100) {
			return true;
		}
		return false;
	}

	public boolean indentfyjudge(Document doc, String url) {
		ArrayList<String> list = new ArrayList<String>();
		String regex = "(http://|https://)?([^/]*)(/?.*)";
		Elements hrefs = doc.select("a[href]");
		Elements httphrefs = hrefs.select("[href^=http]");
		for (Element link : httphrefs) {
			String linkHref = link.attr("href");
			// System.out.println(linkHref);
			String temp = linkHref.replaceAll(regex, "$2");
			int start = temp.indexOf(".");
			int end = temp.indexOf(".", start + 1);
			if (end > start)
				list.add(temp.substring(start + 1, end));
		}
		int mf = 0, f = 0;
		String identfy = "";
		Set<String> uniqueSet = new HashSet<String>(list);
		for (String temp : uniqueSet) {
			f = Collections.frequency(list, temp);
			if (f > mf) {
				mf = f;
				identfy = temp;
			}
		}
		System.out.println("indentfy:" + identfy);
		int ustart = url.indexOf(".");
		int uend = url.indexOf(".", ustart + 1);
		String uidentfy = "";
		if (uend > ustart) {
			uidentfy = url.substring(ustart, uend);
		}
		// ��ҳ��������ҳURL��ݵ�һ����
		if (!uidentfy.equals(identfy)) {
			return false;
		}
		// ָ����ҳURL��ݵ��ⲿ����
		if ((int) (mf / Collections.frequency(list, uidentfy)) < 2) {
			return false;
		}
		return true;
	}

}
