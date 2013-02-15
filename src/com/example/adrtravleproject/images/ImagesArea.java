package com.example.adrtravleproject.images;

import java.util.ArrayList;

import android.database.Cursor;
import android.util.Log;

import com.example.adrtravleproject.AllTheSource;
import com.example.adrtravleproject.mydb.MyDB;
import com.google.android.maps.GeoPoint;

public class ImagesArea {
	private String country, locality, suburb; // 대한민국, 서울시, 강남구
	private String fullAddr; // 대한민국 서울시 강남구 ~ 
	private GeoPoint mGeopoint; // geoPoint of Area instance
	private static ArrayList<String> imgList; // uri of images in same Area
	private ArrayList<ImagesArea> imgAreaData; // list managing instances of ImageArea
	private int dataIdx;
	
	
	
	public ImagesArea(){
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
		int _id = cursor.getInt(3);
		// need to check there is a _id at number 3 in DB
		
	}
}
