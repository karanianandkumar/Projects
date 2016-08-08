package com.example.callblock;

import java.lang.reflect.Method;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

public class CallReceiver extends BroadcastReceiver {

	@SuppressWarnings("static-access")
	@Override
	public void onReceive(Context context, Intent intent) {

		DataBase db = new DataBase(context);
		TelephonyManager telephony = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			Bundle bn = intent.getExtras();
			String num = bn.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
			String numcop = num;
			if (num.length() > 10) {
				num = num.substring(num.length() - 10);
			}
			if (db.exists(num)) {
				Class<?> c = Class.forName(telephony.getClass().getName());
				Method m = c.getDeclaredMethod("getITelephony");
				m.setAccessible(true);
				ITelephony telephonyservice = (ITelephony) m.invoke(telephony);
				//int state = telephony.getCallState();
				if (bn.getString(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
					telephonyservice.endCall();
					this.sendNotification(context, num);
					db.insertLog(num);
					Thread.sleep(4000);
					this.DeleteLastCall(context, numcop);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private void sendNotification(Context context, String phnnum) {
		// Creating NotificationManager
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// Creating Notification
		Notification notification = new Notification(
				android.R.drawable.stat_notify_missed_call, "Call Block",
				System.currentTimeMillis());

		// Setting ContentView
		Intent intent = new Intent(context, Data.class);
		intent.putExtra("Phnnumber", phnnum);
		PendingIntent pending = PendingIntent
				.getActivity(context, 0, intent, 0);
		notification
				.setLatestEventInfo(context, phnnum, "Missed Call", pending);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		nm.notify(phnnum, 1, notification);
	}

	private void DeleteLastCall(Context context, String phnnum) {

		Cursor cursor = context.getContentResolver().query(
				CallLog.Calls.CONTENT_URI, null,
				CallLog.Calls.NUMBER + " = ? ", new String[] { phnnum },
				CallLog.Calls.DATE + " DESC");
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			int idOfRowToDelete = cursor.getInt(cursor
					.getColumnIndex(CallLog.Calls._ID));
			context.getContentResolver().delete(CallLog.Calls.CONTENT_URI,
					CallLog.Calls._ID + " = ? ",
					new String[] { String.valueOf(idOfRowToDelete) });

		}
	}
}