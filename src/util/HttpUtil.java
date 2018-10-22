package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpUtil {

	// public static void main(String[] args) throws IOException {
	//
	// String html = htmlGetWithCookie("http://www.baidu.com", null);
	// System.out.println(html);
	// // String url = "http://cms.pokermanager.club/cms/";
	// // String cookie = getCookiesByGet(url);
	// // String html = htmlGetWithCookie(url, cookie);
	// // String regex = "ng-init=\"([^\"]*)\'\"";
	// // List<String> resultList = StringUtil.match(html, regex);
	// }

	// 获取cookies, 每使用一次与目标网站建立�?次连�?, 创建�?个新的SESSIONID, 慎用
	public static String getCookiesByGet(String url) {
		// 准备返回的参�?
		String cookie = "";

		HttpClient httpClient = new HttpClient();

		// 模拟登陆，按实际服务器端要求选用 Post �? Get 请求方式
		GetMethod postMethod = new GetMethod(url);

		try {
			// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
			httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			int statusCode = httpClient.executeMethod(postMethod);
			// 获得登陆后的 Cookie
			Cookie[] cookies = httpClient.getState().getCookies();
			for (Cookie c : cookies) {
				cookie += c.toString() + ";";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cookie;
	}

	// 获取html页面
	public static String htmlGetWithCookie(String url, String cookie) {

		String html = null;
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		// getMethod.setRequestHeader("cookie", cookie);
		// getMethod.setRequestHeader("Referer", url);
		// getMethod.setRequestHeader("User-Agent",
		// "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like
		// Gecko) Chrome/31.0.1650.63 Safari/537.36");

		try {
			httpClient.executeMethod(getMethod);
			html = getMethod.getResponseBodyAsString();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			// TODO 眼睛
		} catch (IOException e) {
			e.printStackTrace();
			// TODO 眼睛
		}

		return html;
	}

	// 获取html页面
	public static String postWithCookie(String url, String cookie) {

		String html = null;
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("cookie", cookie);
		postMethod.setRequestHeader("Referer", url);
		postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");

		try {
			httpClient.executeMethod(postMethod);
			html = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return html;
	}

	// 登录结果
	public static String postWithCookie(String url, String cookie, Map<String, String> paramMap) {

		HttpClient httpClient = new HttpClient();

		// 模拟登陆，按实际服务器端要求选用 Post �? Get 请求方式
		PostMethod postMethod = new PostMethod(url);

		// 设置登陆时要求的参数
		List<NameValuePair> params = new ArrayList<>();
		Set keySet = paramMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = paramMap.get(key);
			params.add(new NameValuePair(key, value));
		}
		NameValuePair[] data = new NameValuePair[params.size()];
		for (int i = 0; i < params.size(); i++) {
			data[i] = params.get(i);
		}
		// 请求添加参数
		postMethod.setRequestBody(data);
		postMethod.setRequestHeader("cookie", cookie);
		postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");

		// 执行获取结果
		String result = null;
		try {
			httpClient.executeMethod(postMethod);
			result = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result.equals(0)) {
			// PokerDownloadTask pokerDonloadTask = new PokerDownloadTask();
			// pokerDonloadTask.setClubId("888111");
			// pokerDonloadTask.setCookie(cookie);
			// pokerDonloadTask.setDate(new Date());
			//
			// // pokerDonloadTask.setStartTime("2017-10-17");
			// // pokerDonloadTask.setEndTime("2017-10-18");
			// ContextHolderUtils.addDownLoadtask(pokerDonloadTask);
		}

		return result;
	}

	// 获取验证码图�?
	public static void safeCodeGetWithCookie(String url, String cookie) throws Exception {

		URL serverUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
		conn.setRequestMethod("GET");// "POST" ,"GET"
		conn.addRequestProperty("Cookie", cookie);
		conn.addRequestProperty("Accept-Charset", "UTF-8;");// GB2312,
		conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");

		if (((HttpURLConnection) conn).getResponseCode() == 200) {
			// 获取�?有响应头字段
			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历�?有的响应头字�?
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 返回的cookie
			String cookie2 = null;
			if (conn.getHeaderFields().get("Set-Cookie") != null) {
				for (String s : conn.getHeaderFields().get("Set-Cookie")) {
					cookie2 += s;
				}
			}
			System.out.println(cookie2);
		}

		InputStream in = null;
		FileOutputStream out = null;
		try {
			in = conn.getInputStream();
			// BufferedImage bi = ImageIO.read(in);
			File safeCodeImg = new File("F:\\workspace\\treasure_workspace\\treasure\\src\\main\\webapp\\static\\picture\\safeCode\\safeCodeImg.png");
			if (!safeCodeImg.exists())
				safeCodeImg.createNewFile();
			/*
			 * ImageIO.write(bi, "png", safeCodeImg); ImageIO.
			 */

			out = new FileOutputStream(safeCodeImg);
			byte[] buffer = new byte[1024];
			int readLength = 0;
			while ((readLength = in.read(buffer)) > 0) {
				byte[] bytes = new byte[readLength];
				System.arraycopy(buffer, 0, bytes, 0, readLength);
				out.write(bytes);
			}

			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (null != out)
				out.close();
			if (null != in)
				in.close();
		}

		conn.disconnect();
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 获取网络返回�?
	public static InputStream getStreamBySendPost(String urlStr, String cookie) {
		InputStream in = null;

		URL url = null;
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			// TODO 眼睛
			e.printStackTrace();
		}
		URLConnection urlConnection = null;
		try {
			urlConnection = url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// TODO 眼睛
			e.printStackTrace();
		}
		HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

		// true -- will setting parameters
		httpURLConnection.setDoOutput(true);
		// true--will allow read in from
		httpURLConnection.setDoInput(true);
		// will not use caches
		httpURLConnection.setUseCaches(false);
		// setting serialized
		httpURLConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
		// default is GET
		try {
			httpURLConnection.setRequestMethod("POST");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			// TODO 眼睛
			e.printStackTrace();
		}
		httpURLConnection.setRequestProperty("connection", "Keep-Alive");
		httpURLConnection.setRequestProperty("Charsert", "UTF-8");
		// 1 min
		httpURLConnection.setConnectTimeout(60000);
		// 1 min
		httpURLConnection.setReadTimeout(60000);

		httpURLConnection.addRequestProperty("Cookie", cookie);

		// connect to server (tcp)
		try {
			httpURLConnection.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// TODO 眼睛
			e.printStackTrace();
		}

		try {
			in = httpURLConnection.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// TODO 眼睛
			e.printStackTrace();
		} // send request to

		return in;
	}

	public static void main(String[] args) throws IOException {
		String url = "http://172.16.34.7:9080/HXCSS/login.do?method=begin";
		String result = postParams(url, new HashMap<String, String>());
		System.out.println(result);
	}

	// 登录结果
	public static String postParams(String url, Map<String, String> paramMap) throws IOException {

		HttpClient httpClient = new HttpClient();

		PostMethod postMethod = new PostMethod(url);

		// 设置登陆时要求的参数
		List<NameValuePair> params = new ArrayList<>();
		Set keySet = paramMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = paramMap.get(key);
			params.add(new NameValuePair(key, value));
		}
		NameValuePair[] data = new NameValuePair[params.size()];
		for (int i = 0; i < params.size(); i++) {
			data[i] = params.get(i);
		}
		// 请求添加参数
		postMethod.setRequestBody(data);
		// postMethod.setRequestHeader("cookie", cookie);
		postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");

		// 执行获取结果
		String result = null;
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		try {
			httpClient.executeMethod(postMethod);
			result = postMethod.getResponseBodyAsString();
			inputStream = postMethod.getResponseBodyAsStream();

			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			String temp;
			StringBuffer html = new StringBuffer(100);
			while ((temp = bufferedReader.readLine()) != null) {
				html.append(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bufferedReader)
				bufferedReader.close();
			if (null != inputStream)
				inputStream.close();
			if (null != postMethod)
				postMethod.releaseConnection();
		}

		return result;
	}

}
