package com.example.update;


import java.io.InputStream;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class service extends Service 
{
	GPSTracker gps;
	static InputStream inputStream;
	static String final_result="";
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		
		gps = new GPSTracker(this);
		String latitude = String.valueOf(gps.getLatitude());
		String longitude = String.valueOf(gps.getLongitude());
		try{
			connect(latitude,longitude,imei);
		}
		catch(Exception e){
		}
		return START_STICKY;
	}

	public static void connect ( final String lat,final String lng, final String device_id)
	{
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String r = httpClient.httpConnect(lat,lng,device_id,inputStream);
					Log.d("Status",r);
				} catch (Exception ex) {
				}
			}

		});
		thread.start();
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "service stopped", Toast.LENGTH_LONG).show();
		Intent sync = new Intent(this,service.class);
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
