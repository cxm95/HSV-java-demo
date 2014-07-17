package sockettest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.mira.lucene.analysis.IK_CAnalyzer;

public class KeyWord implements FileHandler {
	public static String[][] feature = new String[6][5000]; // �������ַ�������
	public static int[][] featurenum = new int[6][500000]; // �ز����ֵ�Ƶ��
	public static int n = 0; // n������������
	public static HashMap[] hm = new HashMap[6];
	public static int[] s = new int[6];

	/* ���ļ��ж�ȡ��ҳ��Ϣ */
	public String getfile(String s) throws Exception {
		File file = new File(s);
		FileReader reader = new FileReader(file);
		int fileLen = (int) file.length();
		char[] chars = new char[fileLen];
		reader.read(chars);
		String txt = String.valueOf(chars);
		System.out.println(txt);
		return txt;
	}

	/* һ�ֻ��������ִʵķִʷ� */
	public static void testJe(String testString) throws Exception {

		Analyzer analyzer = new IK_CAnalyzer();
		Reader r = new StringReader(testString);
		TokenStream ts = (TokenStream) analyzer.tokenStream("", r);
		Token t;
		String[] strArray = new String[100000];
		BufferedWriter bw = new BufferedWriter(new FileWriter("H:\\test.txt"));
		for (int i = 0; (t = ts.next()) != null; i++) {

			strArray[i] = t.termText();
			bw.write(strArray[i]);
			bw.write(" ");

		}

		bw.close();
	}

	/*
	 * ��ȡ��ҳ�еĹؼ��ʣ�����������ʽƥ�������ַ��������ճ��ִ������ֱ�洢
	 */
	@SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
	public static void getfeature(BufferedReader br, int f) throws IOException {
		String s = br.readLine();
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);

		while (s != null) {
			String[] arr = new String[s.length()];

			for (int i = 0; i < arr.length;) {
				String a = "";
				arr[i] = s.substring(i, (i + 1));
				Matcher m = p.matcher(arr[i]);
				while (m.find() && (!arr[i].equals("��"))
						&& (!arr[i].equals("��")) && (!arr[i].equals("��"))
						&& (!arr[i].equals("��")) && (!arr[i].equals("��"))
						&& (!arr[i].equals("��")) && (!arr[i].equals("��"))) { // �ж�m�Ƿ�Ϊ����
					a = a + arr[i]; // ��feature���鸳ֵ���ж��Ƿ���ڣ�����Ƶ��+1
					if (i + 1 == arr.length) {
						break;
					}
					i++;
					arr[i] = s.substring(i, (i + 1));
					m = p.matcher(arr[i]);

				}

				if (a != "" && (a.length() >= 2)) {
					if (hm[f].containsKey(a)) {
						hm[f].put(a, (Integer) hm[f].get(a) + 1);
						int j = 0;
						for (; a.equals(feature[f][j]) == false; j++) {
						}
						featurenum[f][j]++;
					} else {
						hm[f].put(a, 1);
						feature[f][n] = a;
						featurenum[f][n] = 1;
						n++;
					}
				}
				if (i == arr.length) {
					break;
				} else {
					i++;
				}
			}
			s = br.readLine();
		}

		br.close();
	}

	/*
	 * һ���������������ڿ�������
	 */

	public static int Partition(int a[], String b[], int p, int r) {
		int x = a[r - 1];
		int i = p - 1;
		int temp1;
		String temp2;
		for (int j = p; j <= r - 1; j++) {
			if (a[j - 1] >= x) {
				// swap(a[j-1],a[i-1]);
				i++;
				temp1 = a[j - 1];
				a[j - 1] = a[i - 1];
				a[i - 1] = temp1;
				temp2 = b[j - 1];
				b[j - 1] = b[i - 1];
				b[i - 1] = temp2;
			}
		}
		// swap(a[r-1,a[i+1-1]);
		temp1 = a[r - 1];
		a[r - 1] = a[i + 1 - 1];
		a[i + 1 - 1] = temp1;
		temp2 = b[r - 1];
		b[r - 1] = b[i + 1 - 1];
		b[i + 1 - 1] = temp2;
		return i + 1;

	}

	/*
	 * һ���������򷽷�
	 */
	public static void QuickSort(int a[], String b[], int p, int r) {

		if (p < r) {
			int q = Partition(a, b, p, r);
			QuickSort(a, b, p, q - 1);
			QuickSort(a, b, q + 1, r);

		}

	}

	/*
	 * ����hashmap�洢�ͼ����ؼ��ʣ����䰲�մ�Ƶ��������
	 */
	@Override
	public void handleFile(String filename, String charset,String url)
			throws Exception {
		// TODO Auto-generated method stub
		KeyWord a = new KeyWord();
		String testString4 = a.getfile(filename);
		testJe(testString4);
		hm[0] = new HashMap();
		FileReader fr = new FileReader("H:\\test.txt");
		BufferedReader br = new BufferedReader(fr);
		getfeature(br, 0);
		br.close();
		int k[] = new int[6];
		k[0] = 0;
		for (; feature[0][k[0]] != null; k[0]++) {
		} // ��������
		QuickSort(featurenum[0], feature[0], 1, k[0]);
		for (int i = 0; i < 900; i++) {
			System.out.println(feature[0][i]);
			if (featurenum[0][i] > 0) {
				System.out.println(featurenum[0][i]);
			}
		}
		UserBenefits benefits = new UserBenefits();
		benefits.process(feature[0][1]);
		return;
	}

}
