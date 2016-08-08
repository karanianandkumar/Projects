package com.example.geoalert;

import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Registration extends Activity implements OnClickListener{

	static InputStream inputStream;
	static String final_result="";
	Button b;
	EditText e1,e2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		 b=(Button)findViewById(R.id.button1);
	        b.setOnClickListener(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	@SuppressLint("ShowToast")
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(e1.length()>0 && e2.length()==10){
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String name = e1.getText().toString();
		String num = e2.getText().toString();
		String imei = telephonyManager.getDeviceId();
		connect(name,num,imei);
		if(final_result=="Success")
		{
			SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(this);
			Editor edit=pref.edit();
			edit.putBoolean("register", true);
			edit.commit();
			startActivity(new Intent(this,GeoAlert.class));
		}
		else
		{
			Toast.makeText(this, "Error in connecting", Toast.LENGTH_LONG).show();
		}
		}
		else{
			if(e1.length()==0){
				Toast toast=Toast.makeText(Registration.this, "Enter Your Name", 2000);
				toast.setGravity(Gravity.BOTTOM, 0, 0);
				toast.show();
			}else{
				Toast toast=Toast.makeText(Registration.this, "Enter Your 10 digit Mobile Number", 2000);
				toast.setGravity(Gravity.BOTTOM, 0, 0);
				toast.show();
			}
		}
		}
	public static void connect ( final String name,final String number, final String device_id)
	{
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// nameValuePairs = new ArrayList<NameValuePair>();
					// nameValuePairs.add(new BasicNameValuePair("longitude",
					// ""+longitude)); //Passing user message with Index Value
					// "UserMessage"
					// nameValuePairs.add(new BasicNameValuePair("latitude",
					// ""+latitude));
					// nameValuePairs.add(new
					// BasicNameValuePair("address",address));
					// httpClient.httpConnection(nameValuePairs,inputStream);
					// //Calling httpConnection passing arrayList and
					// InputStream
					String r = httpClient.httpConnect(name,number,device_id,inputStream);
					Log.d("<<<-Inside insertMsgToServer->>>","Success in saving msg into server");
					final_result = r;
					Log.d("final_result output",r);
					// output="connected";
				} catch (Exception ex) {
					Log.d("<<<-Inside insertMsgToServer->>>","Error in saving msg into server");
					// output = "not Connected";
				}// end of exception handling
			}

		});
		thread.start();
	}
	}