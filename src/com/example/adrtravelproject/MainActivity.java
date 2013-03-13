package com.example.adrtravelproject;

import java.io.IOException;
import java.util.List;

import android.app.TabActivity;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TabHost;

import com.example.adrtravelproject.images.ImagesArea;
import com.example.adrtravelproject.mydb.MyDB;

public class MainActivity extends TabActivity{
	
	private Cursor mImageCursor;

    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       setContentView(R.layout.activity_main); 

        /** 
         * Tab ���� mapŬ���� �Ѱ�µ� ����Ʈ �߰� �����൵ �ǳ�... ��ȣ�� ������
         */
        final TabHost mTabHost = getTabHost();
        getLayoutInflater().inflate(com.example.adrtravleproject.R.layout.activity_main, mTabHost.getTabContentView());
       
        mTabHost.addTab(mTabHost.newTabSpec("tab1")
        .setIndicator("Map")
        .setContent(com.example.adrtravleproject.R.id.first));
 //       .setContent(new Intent(this, Map.class)));  
        
        mTabHost.addTab(mTabHost.newTabSpec("tab2")
        .setIndicator("Test")
        .setContent(com.example.adrtravleproject.R.id.second));

        /** init the source such as db **/
        AllTheSource.initialize(getApplicationContext());
        
        /** getting info of images from SD card by using MediaStore contentprovider **/
        mImageCursor = CursorFromSd();
        // mImageCursor ����� ������ Ȯ�� �� �ʿ䰡 �ִ�. �ֳĸ� �ҽ� �ٲٰ� �����߱� ����������
        
        /** Open DB and insert data into DB */
        
        /** �̰� �̵�� ������ ���� ������ �����ϴ���**/
    	InitDB();
        
        /** �̰� ��񿡼�  Img���� Ŭ������ ���� �ѱ�°� **/
    	InitImgData();
    	
    	/** ������� �����ϸ� ������ ������ ���� �̹��� Ŭ������ ��� �� ���� **/
    	
    	
    	/** Get the images which are with geopoint */
 //   	AllTheSource.getInstance().getImagArea().getDataFromDB();
    	
    	
    	
    }
    
    public Cursor CursorFromSd() {
    	String[] proj = null;
    	
    	@SuppressWarnings("deprecation")
		Cursor mCursor = managedQuery( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);
    	if(mImageCursor == null)
    		Log.e("Cursor", "nullError");
    	mCursor.moveToFirst();
    	
    	return mCursor;
    }
    
    public void InitDB() {
    	MyDB mDB;
    	mDB = AllTheSource.getInstance().getDB();
    	mDB.open();
    	
    	while(!mImageCursor.isAfterLast()){    	
	    	int columIndexID = mImageCursor.getColumnIndex(MediaStore.Images.Media._ID);
	    	int columIndexDate = mImageCursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
	    	int columIndexLat = mImageCursor.getColumnIndex(MediaStore.Images.Media.LATITUDE);
	    	int columIndexLon = mImageCursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE);
	    	int columPath = mImageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
	    	int titlecolum = mImageCursor.getColumnIndex(MediaStore.Images.Media.TITLE); 
	    	
	    	// 
	    	String title = mImageCursor.getString(titlecolum);
	    	int img_id = mImageCursor.getInt(columIndexID);
	    	int date_taken = mImageCursor.getInt(columIndexDate);
	    	double lat = mImageCursor.getDouble(columIndexLat);
	    	double longi = mImageCursor.getDouble(columIndexLon);
	    	String path = mImageCursor.getString(columPath);
	    	String memo = null;
	    	
	    	// Inserting image data into DB but it's already done
	    	mDB.createRec(img_id, date_taken, lat, longi, memo, path);
	    	
	    	mImageCursor.moveToNext();
    	} 
    	mDB.close(); 
    	mImageCursor.close();
    }
    
    
    /**
     *  put Images with Geocode into ImagesArea Class 
     * 
     */
    public void InitImgData() {
    	
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
	    	
	    	Geocoder gc = new Geocoder(getApplicationContext());
	    	String addr = null;
	    	String admAr = null;
	    	String ctrCode = null;
	    	String ctrName = null;
	    	String local = null;
	    	try {
				List<Address> _list = gc.getFromLocation(_lat, _longi, 1);
				Address ad = _list.get(0);
				addr = ad.getAddressLine(0); // full addr
				admAr = ad.getAdminArea(); // ���
				ctrCode = ad.getCountryCode(); // kr
				ctrName = ad.getCountryName(); // ���ѹ�
				local = ad.getLocality(); // ������ 

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
	    	**/
	    	if( imgArea.listOfLocal.indexOf(local) == -1 ) {
	    		imgArea.imgAreaData.add( new ImagesArea(ctrName, admAr, local, _path, _memo, _lat, _longi) );
	    		imgArea.listOfLocal.append(local);
	    	}
	    	
	    	else {
	    		int idx = imgArea.listOfLocal.indexOf(local);
	    		imgArea.imgAreaData.get(idx).imgPathList.add(_path);
	    		//������ ������ ���� �ּҸ� ��
	    	}
	    	cursor.moveToNext();
    	}
		
    	mDB.close(); 
    	cursor.close();
		// need to check there is a _id at number 3 in DB
		
	}
    
}
