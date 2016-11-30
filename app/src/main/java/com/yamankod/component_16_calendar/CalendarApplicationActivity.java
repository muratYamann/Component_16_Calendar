package com.yamankod.component_16_calendar;

import java.util.GregorianCalendar;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

public class CalendarApplicationActivity extends Activity {


	int PERMISSIONS_REQUEST_CODE = 1;

	public static final String[] EVENT_PROJECTION = new String[] {
			Calendars._ID, 
			Calendars.ACCOUNT_NAME, 
			Calendars.CALENDAR_DISPLAY_NAME 
	};
	 private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getPermissionReadCalendar();
		getPermissionWrieteCalendar();

	}
	 public void onClick(View view) { 	  
	        Intent intent = new Intent(Intent.ACTION_INSERT); 
	        intent.setType("vnd.android.cursor.item/event"); 
	        intent.putExtra(Events.TITLE, "Android Dersi");
	        intent.putExtra(Events.EVENT_LOCATION, "Kou");
	        intent.putExtra(Events.DESCRIPTION, "Content Provider Örneği"); 	   
	        GregorianCalendar calDate = new GregorianCalendar(2016, 11, 30);
	        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 
	                calDate.getTimeInMillis()); 
	        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 
	                calDate.getTimeInMillis()); 
	        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true); 
	        intent.putExtra(Events.RRULE, 
	                "FREQ=WEEKLY;COUNT=11;WKST=SU;BYDAY=TU,TH"); 
	        intent.putExtra(Events.ACCESS_LEVEL, Events.ACCESS_PRIVATE); 
	        intent.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY); 
	        startActivity(intent); 
	    }  
	 public void queryCalendar(View view) { 
	        Cursor cur = null; 
	        ContentResolver cr = getContentResolver(); 
	        Uri uri = Calendars.CONTENT_URI; 
	        String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND (" 
	                + Calendars.ACCOUNT_TYPE + " = ?))"; 
	   
	        String[] selectionArgs = new String[] { "suhap.sahin@gmail.com",
	                "com.google" }; 
	        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null); 
	   
	        while (cur.moveToNext()) { 
	            String displayName = null; 
	            displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX); 
	            Toast.makeText(this, "Calendar " + displayName, Toast.LENGTH_SHORT) 
	                    .show(); 
	        } 
	    }

	/**
	 *
	 <uses-permission android:name="android.permission.READ_CALENDAR" />
	 <uses-permission android:name="android.permission.WRITE_CALENDAR" />
	 */

	public void getPermissionReadCalendar (){
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
				!= PackageManager.PERMISSION_GRANTED) {


			if (shouldShowRequestPermissionRationale(
					Manifest.permission.READ_CALENDAR)) {

			}
			requestPermissions(new String[]{Manifest.permission.READ_CALENDAR},
					PERMISSIONS_REQUEST_CODE);
		}
	}


	public void getPermissionWrieteCalendar (){
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
				!= PackageManager.PERMISSION_GRANTED) {


			if (shouldShowRequestPermissionRationale(
					Manifest.permission.WRITE_CALENDAR)) {

			}
			requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR},
					PERMISSIONS_REQUEST_CODE);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
		if (requestCode == PERMISSIONS_REQUEST_CODE) {
			if (grantResults.length == 1 &&
					grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
			}
		} else {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}

}
