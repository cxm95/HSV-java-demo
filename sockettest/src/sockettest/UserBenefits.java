package sockettest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class UserBenefits {
	private String base="http://www.so.com/s?ie=utf-8&shb=1&src=360sou_newhome&q=";
	private String temp1,temp2;
	public void process(String keyword1) throws IOException
	{
		temp1=base+java.net.URLEncoder.encode(keyword1);
		System.out.println(temp1);
		String s;
		int indexofbase=-1;
		
		FileReader fr = new FileReader(
				".\\phishing\\phishing2.html");
		BufferedReader br = new BufferedReader(fr);
		
		File html = new File(".\\phishing\\phishing.html");
		if (!html.exists()) {
			html.createNewFile();
		}
		FileWriter fw=new FileWriter(html);
		
		s = br.readLine();
		while ((s = br.readLine()) != null) {
			if(s.indexOf(base)!=-1)
			{
				s=s.replace(base, temp1);
			}
			fw.write(s);
		}
		br.close();
		fw.close();
		fr.close();
		
		return;
	}
}
