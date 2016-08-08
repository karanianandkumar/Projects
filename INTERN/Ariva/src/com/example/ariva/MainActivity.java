package com.example.ariva;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.apache.cordova.*;
public class MainActivity extends DroidGap {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		super.setIntegerProperty("splashscreen", R.drawable.spalsh);
		super.loadUrl("file:///android_asset/www/index.html",5000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.help: {
			AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
			alertDialog.setTitle("HELP");
			alertDialog.setIcon(R.drawable.help);
			alertDialog.setMessage("Choose a source and destination(Assuming that source is your current location).Then it displays buses with arrival times. Arrival time means time required for the bus to reach from its current location to the source, travel time is the time required from source to destination.");
			alertDialog.show();
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
