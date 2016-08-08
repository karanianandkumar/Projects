package com.example.callblock;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Data extends Activity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hist);
		DataBase db = new DataBase(this);
		String phnnum = getIntent().getExtras().getString("Phnnumber");
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancelAll();
		try {
			Cursor c = db.showLog(phnnum);
			if (c.getCount() > 0) {
				startManagingCursor(c);
				ListView lv = (ListView) findViewById(R.id.history);
				String[] FROM = { "Time" };
				int[] TO = { R.id.num };
				SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
						R.layout.row, c, FROM, TO);
				lv.setAdapter(adapter);
			} else {
				Toast.makeText(this, "No Details", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			// Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

}
