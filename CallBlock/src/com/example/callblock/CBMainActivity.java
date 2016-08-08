package com.example.callblock;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CBMainActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	Button a, d;
	TextView tv;
	DataBase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cbmain);
		a = (Button) findViewById(R.id.add);
		d = (Button) findViewById(R.id.del);
		a.setOnClickListener(this);
		d.setOnClickListener(this);
		db = new DataBase(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cbmain, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		if (id == R.id.add)
			startActivity(new Intent(this, Add.class));
		else if (id == R.id.del) {
			Intent in = new Intent(CBMainActivity.this, Del.class);
			startActivity(in);
		}
	}

	@SuppressWarnings("deprecation")
	protected void onStart() {
		super.onStart();

		Cursor c = db.numscursor();
		ListView lv = (ListView) findViewById(R.id.main_num_list);
		startManagingCursor(c);
		final String[] FROM = { "Number" };
		final int[] TO = { R.id.num };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.row, c, FROM, TO);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String phnnum = ((TextView) arg1).getText().toString();
		Intent in = new Intent(CBMainActivity.this, Data.class);
		in.putExtra("Phnnumber", phnnum);
		startActivity(in);
	}

}
