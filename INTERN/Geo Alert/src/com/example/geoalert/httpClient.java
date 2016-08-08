package com.example.geoalert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class httpClient {
	/**
	 * @param nameValuePairs
	 *            passing arraylist with all information added
	 * @param inputStream
	 *            passing inputStream from user request
	 */
	public static String geofence_create(String latitude,String longitude,String device_id,String width,String height,java.io.InputStream inputStream)
	{
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://itsmygrid.com/tste/submit.php?lat="+latitude+"&lng="+longitude+"&imei="+device_id+"&width="+width+"&height="+height);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (Exception e) 
		{
			//geofence_create(latitude, longitude, device_id, inputStream);
			return "Error in Http Connection";
		}
	}
	public static String httpConnect(String name,String number,String device_id,java.io.InputStream inputStream)
	{
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://itsmygrid.com/tste/response.php?name="+name+"&number="+number+"&imei="+device_id);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
			Log.d("<<<-http_log_tag->>>", "Success in Http Connection");

			return "Success";
		} catch (Exception e) {
			Log.e("<<<-http_log_tag->>>","Error in Http Connection" + e.toString());
			return "failure";
		}
	}
}
