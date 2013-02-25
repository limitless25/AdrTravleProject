package com.example.adrtravleproject.images;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.example.adrtravleproject.AllTheSource;
import com.example.adrtravleproject.mydb.MyDB;
import com.google.android.maps.GeoPoint;

public class ImagesArea {
	private String country, state, locality; // 대한민국, 서울시, 강남구
	private String fullAddr; // 대한민국 서울시 강남구 ~ 
	private GeoPoint mGeopoint; // geoPoint of Area instance
	double lat, longi;
	String memo; 
	
	
	private ArrayList<String> imgPathList = new ArrayList<String>(); // uri of images in same Area
	private static ArrayList<ImagesArea> imgAreaData = new ArrayList<ImagesArea>(); // list managing instances of ImageArea
	private int dataIdx;
	private Context mCtx;
	
	public ImagesArea(Context ctx) {
		mCtx = ctx;
	}
	
	public ImagesArea(String mctrName, String madmAr, String mlocal, String mpath, String mmemo, double mLat, double mLongi){
		country = mctrName;
		state = madmAr;
		locality = mlocal;
		imgPathList.add(mpath);
		memo = mmemo;
		lat = mLat;
		longi = mLongi;
	}
	
	public void getDataFromDB(){
		MyDB mDB;
		mDB = AllTheSource.getInstance().getDB();
		
		mDB.open();
		Cursor cursor = mDB.fetchRecWithGeo();
		if(cursor == null){
			Log.e("no Cursor", "No Images with GeoPoint");
		//	throw new Exception("No images with GeoPoint");
		}
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){ 
	    	// 
	    	int _img_id = cursor.getInt(3);
	    	int date_taken = cursor.getInt(2);
	    	double _lat = cursor.getDouble(0);
	    	double _longi = cursor.getDouble(1);
	    	String _memo = cursor.getString(5);
	    	String _path = cursor.getString(4);

	    	
	    	/** Initialize the ImagesArea class **/
	    	
	    	/** 
	    	 *  i need to check which suburb these _lat and _longi belong, gangnam or sth
	    	 */
	    	
	    	Geocoder gc = new Geocoder(mCtx);
	    	String addr = null;
	    	String admAr = null;
	    	String ctrCode = null;
	    	String ctrName = null;
	    	String local = null;
	    	try {
				List<Address> _list = gc.getFromLocation(_lat, _longi, 1);
				Address ad = _list.get(0);
				addr = ad.getAddressLine(0); // full addr
				admAr = ad.getAdminArea(); // 경기
				ctrCode = ad.getCountryCode(); // kr
				ctrName = ad.getCountryName(); // 대한민
				local = ad.getLocality(); // 군포시 

//				String subArea = ad.getSubAdminArea();
//				String subLocal = ad.getSubLocality();
//				String ftName = ad.getFeatureName();
				
//				Locale l = ad.getLocale();
//				Bundle b = ad.getExtras();
				
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	/** test for putting data into class value 
	    	 * this is for the new Area
	    	**/
	    	imgAreaData.add( new ImagesArea(ctrName, admAr, local, _path, _memo, _lat, _longi) );
	    	
	    	/** is temp same as imgAreaData.get(0) by "temp = imgAreadata.get(0); "
	    	 * if i change sth in temp, imgAreaData.get(0) also would be changed?
	    	 */
	    	cursor.moveToNext();
    	}
		
    	mDB.close(); 
    	cursor.close();
		// need to check there is a _id at number 3 in DB
		
	}
}
