package com.example.adrtravleproject;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.example.adrtravleproject.mydb.MyDB;

public class MainActivity extends Activity{
	
	private Cursor mImageCursor;

    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  
        
        /** init the source such as db **/
        AllTheSource.initialize(getApplicationContext());
        
        
        /** getting info of images from SD card by using MediaStore contentprovider **/
    	String[] proj = null;
    	mImageCursor = managedQuery( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);
    	if(mImageCursor == null)
    		Log.e("Cursor", "nullError");
    	mImageCursor.moveToFirst();
    	
    	
    	/** 문제는 한번 push했는데 또하면 안된다는 거.....주석처리...0000000000000000000000000**/
    	MyDB mDB;
    	mDB = AllTheSource.getInstance().getDB();
    	mDB.open();
    	
    	while(!mImageCursor.isAfterLast()){    	
	    	int columIndexID = mImageCursor.getColumnIndex(MediaStore.Images.Media._ID);
	    	int columIndexDate = mImageCursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
	    	int columIndexLat = mImageCursor.getColumnIndex(MediaStore.Images.Media.LATITUDE);
	    	int columIndexLon = mImageCursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE);
	    	int titlecolum = mImageCursor.getColumnIndex(MediaStore.Images.Media.TITLE);    	
	    	
	    	// 
	    	String title = mImageCursor.getString(titlecolum);
	    	int img_id = mImageCursor.getInt(columIndexID);
	    	int date_taken = mImageCursor.getInt(columIndexDate);
	    	int lat = (int)mImageCursor.getDouble(columIndexLat);
	    	int longi = (int)mImageCursor.getDouble(columIndexLon);
	    	String memo = null;
	    	
	    	mDB.createRec(img_id, date_taken, lat, longi, memo);
	    	
	    	mImageCursor.moveToNext();
    	}
    	mDB.close();
    	mImageCursor.close();
    }
}
