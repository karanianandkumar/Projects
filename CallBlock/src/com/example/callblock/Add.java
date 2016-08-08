package com.example.callblock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add extends Activity implements OnClickListener {
	Button add;
	EditText et;
	String s, toasttext, query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addscreen);
		add = (Button) findViewById(R.id.button1);
		et = (EditText) findViewById(R.id.editText1);
		et.setHint("10 digit number");
		add.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		s = et.getText().toString();
		toasttext = "Number Added Successfully";
		try {
			if (s.length() > 10) {
				s = s.substring(s.length() - 10);
			}
			long phnnum = Long.parseLong(s);
			DataBase db = new DataBase(this);
			db.insert(phnnum);
			db.logTable(s);
			startActivity(new Intent(Add.this, CBMainActivity.class));
			finish();
		} catch (Exception e) {
			toasttext = "Invalid Number";
		}
		Toast.makeText(Add.this, toasttext, Toast.LENGTH_SHORT).show();
	}
}
