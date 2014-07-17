package dao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlFliter {
	public static boolean urlcheck(String url) {
		String regex = "[a-zA-z]+://[^\\s]*";
		Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher ma = pa.matcher(url);
		if (ma.find()) {
			return true;
		}
		return false;
	}

}
