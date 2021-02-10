package com.zzg.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;

import com.alibaba.fastjson.JSONObject;

public class HttpUtils {
	public static String getBodyString(ServletRequest request) {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			inputStream = request.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static boolean isAjaxRequest(HttpServletRequest request) {
		if (request.getHeader(HttpHeaders.ACCEPT) != null
				&& request.getHeader(HttpHeaders.ACCEPT).indexOf("application/json") > -1
				|| (request.getHeader("X-Requested-With") != null
						&& request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest"))) {
			return true;
		}
		return false;
	}

	public static String doGet(String url, Map<String, Object> map) {
		String strResult = "";
		// 4. 获取默认的client实例
		CloseableHttpClient client = HttpClients.createDefault();
		try {

			// 1.创建URIBuilder
			URIBuilder uriBuilder = new URIBuilder(url);

			// 2.设置请求参数
			if (map != null) {
				// 遍历请求参数
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					// 封装请求参数
					uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
				}
			}

			// 3.创建请求对象httpGet
			HttpGet httpGet = new HttpGet(uriBuilder.build());

			// 4.使用httpClient发起请求
			CloseableHttpResponse response = client.execute(httpGet);
			try {
				// 5. 获取响应entity
				HttpEntity respEntity = response.getEntity();
				strResult = EntityUtils.toString(respEntity, "UTF-8");
			} finally {
				// 6. 关闭响应对象
				response.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 7. 关闭连接，释放资源
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return strResult;
	}

	/**
	 * 普通POST提交
	 * 
	 * @param url
	 * @param map
	 * @return
	 */
	public static String doPost(String url, Map<String, Object> map) {
		String strResult = "";
		// 1. 获取默认的client实例
		CloseableHttpClient client = HttpClients.createDefault();
		// 2. 创建httppost实例
		HttpPost httpPost = new HttpPost(url);
		// 3. 创建参数队列（键值对列表）
		List<NameValuePair> paramPairs = new ArrayList<>();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			Object val = map.get(key);
			paramPairs.add(new BasicNameValuePair(key, val.toString()));
		}
		UrlEncodedFormEntity entity;
		try {
			// 4. 将参数设置到entity对象中
			entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
			// 5. 将entity对象设置到httppost对象中
			httpPost.setEntity(entity);
			// 6. 发送请求并回去响应
			CloseableHttpResponse resp = client.execute(httpPost);
			try {
				// 7. 获取响应entity
				HttpEntity respEntity = resp.getEntity();
				strResult = EntityUtils.toString(respEntity, "UTF-8");
			} finally {
				// 9. 关闭响应对象
				resp.close();
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 10. 关闭连接，释放资源
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return strResult;
	}

	public static String doDelete(String url, Map<String, Object> map) {
		String strResult = "";
		// 1. 获取默认的client实例
		CloseableHttpClient client = HttpClients.createDefault();

		try {
			// 2.创建URIBuilder
			StringBuilder builder = new StringBuilder();
			builder.append(url);
			// 3.设置请求参数
			if (map != null) {
				// 遍历请求参数
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					// 封装请求参数
					builder.append("/").append(entry.getValue().toString());
				}
			}
			// 4. 创建httpDelete实例
			HttpDelete httpDelete = new HttpDelete(builder.toString());
			// 5.使用httpClient发起请求
			CloseableHttpResponse response = client.execute(httpDelete);
			try {
				// 5. 获取响应entity
				HttpEntity respEntity = response.getEntity();
				strResult = EntityUtils.toString(respEntity, "UTF-8");
			} finally {
				// 6. 关闭响应对象
				response.close();
			}

		} catch (Exception e) {
		} finally {
			// 7. 关闭连接，释放资源
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return strResult;
	}

	public static String invokeIdfRestApi(String url, Map<String, Object> jsonMap) {
		String result = null;
		HttpPost request = new HttpPost(url);
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-Type", "application/json");
		request.setHeader("User-Agent", "Mozilla/4.5");
		if (jsonMap != null) {
			try {
				JSONObject json = new JSONObject(jsonMap);
				// 发送数据
				StringEntity entity = new StringEntity(json.toString(), "UTF-8");
				request.setEntity(entity);
				// 获得响应对象
				HttpResponse response = getHttpResponse(request);
				// 判断是否请求成功
				if (response.getStatusLine().getStatusCode() == 200) {
					// 获得响应
					result = EntityUtils.toString(response.getEntity());
					return result;
				}
			} catch (Exception e) {
				result = "网络异常！" + e.getMessage();
				return result;
			}
		}
		return null;
	}

	/**
	 * 根据请求获得响应对象 response *
	 */
	public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException {
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		enableSSL(defaultHttpClient);
		defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		HttpResponse response = defaultHttpClient.execute(request);
		return response;
	}

	private static void enableSSL(DefaultHttpClient httpclient) {
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
			SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme https = new Scheme("https", sf, 443);
			httpclient.getConnectionManager().getSchemeRegistry().register(https);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重写验证方法，取消检测 ssl
	 */
	private static TrustManager truseAllManager = new X509TrustManager() {
		public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};

}
