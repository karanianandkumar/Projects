package com.example.geoalert;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class TrafficData extends Activity {

	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showdata);
		WebView description = (WebView) findViewById(R.id.webView);
		description.setWebChromeClient(new WebChromeClient());

		WebSettings webSettings = description.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setAllowContentAccess(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setLoadsImagesAutomatically(true);

		SqlDataBase db = new SqlDataBase(this);
		Cursor c = db.retriveData();
		startManagingCursor(c);
		// c.close();
		String webdata = "<table>";
		c.moveToFirst();
		if (c.getCount() > 0) {
			webdata = webdata
					+ "<tr><th>Number</th><th>Name</th><th>Status</th><th>Time</th></tr>";

			for (int i = 0; i < c.getCount(); i++) {
				String lat = c.getString(c.getColumnIndex("num"));
				String lng = c.getString(c.getColumnIndex("name"));
				String time = c.getString(c.getColumnIndex("time"));
				String dst = c.getString(c.getColumnIndex("status"));
				webdata = webdata + "<tr><td>" + lat + "</td><td>" + lng
						+ "</td><td>" + time + "</td><td>" + dst + "</td></tr>";

				c.moveToNext();
			}
			webdata = webdata + "</table>";
		} else {
			webdata = webdata + "No data available";
		}
		c.close();
		String htmlData = "";
		htmlData = "<!DOCTYPE html><html><head><style>table,td,tr{border:3px solid lightgray;}</style></head><body>"
				+ webdata + "</body></html>";
		description.loadData(htmlData, "text/html", "UTF-8");
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dataclear, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.clear: {
			clear();
			finish();
			//startActivity(this,TrafficData.class);
			Toast.makeText(this,"Cleared Database",Toast.LENGTH_SHORT).show();
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void clear() {
		SqlDataBase db = new SqlDataBase(this);
		db.delete();
		db.close();
	}
}
