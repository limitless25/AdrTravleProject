package com.example.adrtravelproject.images;

import java.util.ArrayList;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

public class ImagesArea {
	public String country, state, locality; // 대한민국, 서울시, 강남구
	public String fullAddr; // 대한민국 서울시 강남구 ~ 
	private LatLng mLoc; // geoPoint of Area instance
	double lat, longi;
	String memo; // 메모는 사진 마다 따로 저장 되어야 하는데...못했다 음 이대로라면 지역마다 메모 한
	// 아니면 승훈이 처럼 이미지 클래스를 따로 만들어서 저장해도 된다. 이건 추후 이야기해보
	
	
	public ArrayList<String> imgPathList = new ArrayList<String>(); // uri of images in same Area
	public static ArrayList<ImagesArea> imgAreaData = new ArrayList<ImagesArea>(); // list managing instances of ImageArea
	public static StringBuffer listOfLocal = new StringBuffer();
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
		mLoc = new LatLng(lat, longi);
	}
	/*
	public void getDataFromDB(){
		MyDB mDB;
		mDB = AllTheSource.getInstance().getDB();
		ImagesArea imgArea = AllTheSource.getInstance().getImagArea();
		
		
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
	    	/*
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
	    	 * need to check whether local is repetition or not
	    	 */
	    	 
	    	/** this is for the new Area
	    	**//*
	    	if( imgArea.listOfLocal.indexOf(local) == -1 ) {
	    		imgArea.imgAreaData.add( new ImagesArea(ctrName, admAr, local, _path, _memo, _lat, _longi) );
	    		imgArea.listOfLocal.append(local);
	    	}
	    	
	    	else {
	    		int idx = imgArea.listOfLocal.indexOf(local);
	    		imgArea.imgAreaData.get(idx).imgPathList.add(_path);
	    		//지역이 같으면 사진 주소만 추
	    	}
	    	
	    	cursor.moveToNext();
    	}
		
    	mDB.close(); 
    	cursor.close();
		// need to check there is a _id at number 3 in DB
		
	}
	*/
	public LatLng getLoc() {
		return mLoc;
	}
}
