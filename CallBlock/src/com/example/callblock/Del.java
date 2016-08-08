package com.example.callblock;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Del extends Activity implements OnItemClickListener {

	Cursor c;
	SimpleCursorAdapter adapter;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delscreen);
		DataBase db = new DataBase(this);
		c = db.numscursor();
		if (c.getCount() == 0) {
			((TextView) findViewById(R.id.DelText)).setText("List is Empty!");
		}
		startManagingCursor(c);
		final String[] FROM = { "Number" };
		final int[] TO = { R.id.num };
		adapter = new SimpleCursorAdapter(this, R.layout.row, c, FROM, TO);
		ListView lv = (ListView) findViewById(R.id.del_items_list);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String str = ((TextView) arg1).getText().toString();
		long phnnum = Long.parseLong(str);
		DataBase db = new DataBase(this);
		db.delete(phnnum);
		db.delLog(str);
		Toast.makeText(this, str + " Deleted Successfully!", Toast.LENGTH_SHORT)
				.show();
		startActivity(new Intent(this, Del.class));
		finish();
	}

}
