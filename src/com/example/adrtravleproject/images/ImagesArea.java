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
	private String country, locality, suburb; // 대한민국, 서울시, 강남구
	private String fullAddr; // 대한민국 서울시 강남구 ~ 
	private GeoPoint mGeopoint; // geoPoint of Area instance
	double lat, longi;
	private ArrayList<String> imgList; // uri of images in same Area
	private static ArrayList<ImagesArea> imgAreaData = new ArrayList<ImagesArea>(); // list managing instances of ImageArea
	private int dataIdx;
	private Context mCtx;
	
	
	
	public ImagesArea(int lat, int longi, String memo, String path){
	//	imgAreaData = new ArrayList<ImagesArea>();
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
//	    	int date_taken = cursor.getInt(2);
	    	double _lat = cursor.getDouble(0);
	    	double _longi = cursor.getDouble(1);
	    	String _memo = cursor.getString(4);
	    	String _path = cursor.getString(5);

	    	
	    	/** Initialize the ImagesArea class **/
	    	
	    	/** 
	    	 *  i need to check which suburb these _lat and _longi belong, gangnam or sth
	    	 */
	    	
	    	Geocoder gc = new Geocoder(mCtx);
	    	try {
				List<Address> _list = gc.getFromLocation(_lat, _longi, 1);
				Address ad = _list.get(0);
				String addr = ad.getAddressLine(0);
				String admAr = ad.getAdminArea();
				String ctrCode = ad.getCountryCode();
				String ctrName = ad.getCountryName();
				String ftName = ad.getFeatureName();
				String locality = ad.getLocality();
				String subArea = ad.getSubAdminArea();
				String subLocal = ad.getSubLocality();
				
//				Locale l = ad.getLocale();
//				Bundle b = ad.getExtras();
				
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    	
	    	
	    	/** test for putting data into class value 
	    	 * this is for the new Area
	    	**/
	    	ImagesArea temp;
	    	temp = imgAreaData.get(0);
	    	temp.imgList.add(_path);
	    	temp.lat = _lat;
	    	temp.longi = _longi;
	    	/** is temp same as imgAreaData.get(0) by "temp = imgAreadata.get(0); "
	    	 * if i change sth in temp, imgAreaData.get(0) also would be changed?
	    	 */
	    	cursor.moveToNext();
    	}
		
    	mDB.close(); 
    	cursor.close();
		int _id = cursor.getInt(3);
		// need to check there is a _id at number 3 in DB
		
	}
}
