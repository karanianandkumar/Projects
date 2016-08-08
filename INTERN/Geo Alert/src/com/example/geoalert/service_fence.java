package com.example.geoalert;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class service_fence extends Service 
{
	GPSTracker gps;
	public final static double AVERAGE_RADIUS_OF_EARTH = 6371;
	NotificationManager notificationManager;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		gps = new GPSTracker(this);
		double latitude = gps.getLatitude();
		double longitude = gps.getLongitude();
		SqlDataBase db = new SqlDataBase(this);
		int i=0;
		try{
			JSONObject jsonObj = getJSONfromURL("http://itsmygrid.com/tste/geo_checking.php?imei="+imei+"&lat="+latitude+"&lng="+longitude);
			int length = Integer.parseInt(jsonObj.getString("length"));
			Log.d("length",""+length);
			while(i<length) 
			{
				String j = ""+i;
				JSONObject one = jsonObj.getJSONObject(j);
				String name = one.getString("name");
				String number = one.getString("number");
				String status = one.getString("status");
				//Log.d(name,status);
				Calendar c = Calendar.getInstance();
				int s = c.get(Calendar.SECOND);
				int m = c.get(Calendar.MINUTE);
				int h = c.get(Calendar.HOUR);
				String time = String.format("%d:%d:%d",h,m,s);
				//Log.d("data","inserted");
				//Notification
				if(status.equals("Entered"))
					display(number,name+", entered into your fence",i);
				else if(status.equals("Existed"))
				{
					status = "Left";
					display(number,name+", left from your fence",i);
				}
				db.insertData(number,name, time, status);
	
				i++;
			}
		}
		catch(Exception e){}
		finally
		{
			db.close();
		}
		return START_STICKY;
		
	}
	@SuppressWarnings({ "deprecation" })
	public void display(String title,String text,int id)
	{
		Notification notification = new Notification(R.drawable.alert,"GeoFence",System.currentTimeMillis());
		notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
		Intent notificationIntent = new Intent(this, MainActivity.class);
	    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0); 
	    notification.setLatestEventInfo(getBaseContext(), title, text, pendingIntent);
	    notificationManager.notify(id, notification);
	}
	
	
	public static JSONObject getJSONfromURL(String url) 
	{
		// initialize
		InputStream is = null;
		String result = "";
		JSONObject jObject = null;

		// http post
		try {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		is = entity.getContent();

		} catch (Exception e) {
		Log.e("log_tag", "Error in http connection " + e.toString());
		}

		// convert response to string
		try {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
		is, "iso-8859-1"), 8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
		sb.append(line + "\n");
		}
		is.close();
		result = sb.toString();
		} catch (Exception e) {
		Log.e("log_tag", "Error converting result " + e.toString());
		}
		// try parse the string to a JSON object
		try {
		jObject = new JSONObject(result);
		} catch (JSONException e) {
		Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return jObject;
		}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "service stopped", Toast.LENGTH_LONG).show();
		Intent sync = new Intent(this,service_fence.class);
		PendingIntent pi = PendingIntent.getService(this, 9191, sync,PendingIntent.FLAG_CANCEL_CURRENT);
		@SuppressWarnings("static-access")
		AlarmManager am = (AlarmManager) getSystemService(this.ALARM_SERVICE);
		am.cancel(pi);
		super.onDestroy();
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
