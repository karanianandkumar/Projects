package com.example.geoalert;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geo_alert);
		SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(this);
        boolean registered=pref.getBoolean("register", false);
        
        if(registered){
        	startActivity(new Intent(this,GeoAlert.class));
        }else{
        	startActivity(new Intent(this,Registration.class));
        }
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
}
