package com.example.update;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class httpClient {
	public static String httpConnect(String lat,String lng,String device_id,java.io.InputStream inputStream)
	{
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://itsmygrid.com/tste/update_loc.php?lat="+lat+"&lng="+lng+"&imei="+device_id);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
			return "Success";
		} catch (Exception e) {
			return "failure";
		}
	}
}
