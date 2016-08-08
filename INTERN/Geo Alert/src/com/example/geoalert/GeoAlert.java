package com.example.geoalert;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class GeoAlert extends Activity {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geo_alert);
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
		case R.id.item1: {
			bgService();
			return true;
		}
		case R.id.item2: {
			stopService(new Intent(getBaseContext(), service_fence.class));
			return true;
		}

		case R.id.item3: {
			startActivity(new Intent(this,GeoFence.class));
			return true;
		}
		case R.id.item4: {
			startActivity(new Intent(this,TrafficData.class));
			return true;
		}
		case R.id.item5: {
			startActivity(new Intent(this, about.class));
			return true;
		}
		case R.id.item6: {
			finish();
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean bgService() {
		Toast.makeText(getApplicationContext(), "service started",
				Toast.LENGTH_SHORT).show();
		Intent synci = new Intent(this, service_fence.class);
		PendingIntent pii = PendingIntent.getService(this, 9191, synci,PendingIntent.FLAG_CANCEL_CURRENT);
		// registering our pending intent with alarmmanager
		@SuppressWarnings("static-access")
		AlarmManager am = (AlarmManager) getSystemService(this.ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),1000 * 60, pii);
		startService(new Intent(getBaseContext(), service_fence.class));
		return true;
	}

}
