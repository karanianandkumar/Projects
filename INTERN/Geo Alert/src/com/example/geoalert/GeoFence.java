package com.example.geoalert;

import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
public class GeoFence extends Activity
{
	static InputStream inputStream;
	static String htmlData;
	GPSTracker gps;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showgeofence);
		
		WebView des = (WebView) findViewById(R.id.View);
		des.setWebChromeClient(new WebChromeClient());
		
		WebSettings webSettings = des.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setAllowContentAccess(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setLoadsImagesAutomatically(true);
		
		gps = new GPSTracker(this);
		String lat = Double.toString(gps.getLatitude());
		String lng = Double.toString(gps.getLongitude());
		
		Display display = getWindowManager().getDefaultDisplay(); 
		@SuppressWarnings("deprecation")
		String width = String.valueOf(display.getWidth());  // deprecated
		@SuppressWarnings("deprecation")
		String height = String.valueOf(display.getHeight());
		
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		connect(lat,lng,imei,width,height);
		des.loadUrl("http://itsmygrid.com/tste/submit.php?lat="+lat+"&lng="+lng+"&imei="+imei+"&width="+width+"&height="+height);
		
	}
		public static void connect ( final String lat,final String lng, final String imei,final String width,final String height)
		{
			Thread thread = new Thread(new Runnable() 
			{
				@Override
				public void run()
				{
					try 
					{
						String r = httpClient.geofence_create(lat, lng, imei,width,height, inputStream);
						htmlData = r;
						Log.d("Final output",r);
					} 
					catch (Exception ex) 
					{
						htmlData = "Error while Connecting to Internet";
					}
				}

			});
			thread.start();	
		}
}
