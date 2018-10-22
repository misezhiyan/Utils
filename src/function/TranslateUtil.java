package function;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

import util.HttpUtil;

public class TranslateUtil {
	public static void main(String[] args) throws UnsupportedEncodingException {

		// String q = "我爱我媳妇";
		// String q = "Good Morning/Afternoon/Evening!(根据实际情况修改)";
		String q = "Good Morning/Afternoon/Evening!";
		char char0 = q.charAt(0);
		// boolean matchChinese = PatternUtil.matchChinese(char0);

		String translateResult = translateResult(q, "en", "zh");
		System.out.println(translateResult);

	}
	

	private static String translateResult(String q, String from, String to) throws UnsupportedEncodingException {

		String url = "http://api.fanyi.baidu.com/api/trans/vip/translate";

		from = empty(from) ? "zh" : from;

		to = empty(to) ? "en" : to;

		String appid = "20180103000111543";

		String p = "hKhTn4umupyJxY61Beny";

		Random random = new Random();
		int salt_int = random.nextInt();
		String salt = String.valueOf(salt_int);

		String sign_source = appid + q + salt_int + p;
		String sign = DigestUtils.md5Hex(sign_source);

		q = URLEncoder.encode(q, "UTF-8");
		String result_url = url + "?" + "q=" + q + "&from=" + from + "&to=" + to + "&appid=" + appid + "&salt=" + salt
				+ "&sign=" + sign;

		String result = HttpUtil.htmlGetWithCookie(result_url, null);

		return result;
	}

	private static boolean empty(String str) {

		return str == null || "".equals(str);
	}

}
