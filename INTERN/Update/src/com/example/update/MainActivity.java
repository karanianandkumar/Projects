package com.example.update;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
		case R.id.start: {
			bgService();
			return true;
		}
		case R.id.stop: {
			stopService(new Intent(this,service.class));
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public boolean bgService() {
		Toast.makeText(getApplicationContext(), "service started",
				Toast.LENGTH_SHORT).show();
		Intent sync = new Intent(this, service.class);
		PendingIntent pii = PendingIntent.getService(this, 9191, sync,PendingIntent.FLAG_CANCEL_CURRENT);
		// registering our pending intent with alarmmanager
		@SuppressWarnings("static-access")
		AlarmManager am = (AlarmManager) getSystemService(this.ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),1000 * 30, pii);
		startService(new Intent(getBaseContext(), service.class));
		return true;
	}

    
}
