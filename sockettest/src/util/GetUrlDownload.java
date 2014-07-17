package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.AddDao;

public class GetUrlDownload {
	public String geturldownload(String s) {
		String regex;
		String downloadurl = "";
		List<String> list = new ArrayList<String>();
		regex = "http.*?,";

		Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		for (int i = 0; i < list.size(); i++) {
			String temp = (String) list.get(i);
			if (temp.indexOf("phishtank") != -1) {
				continue;
			}
			System.out.println(temp);
			downloadurl = downloadurl + temp;

		}
		downloadurl = downloadurl.replaceAll(",", " ");
		return downloadurl;
	}

}