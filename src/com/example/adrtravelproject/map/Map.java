package com.example.adrtravelproject.map;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adrtravleproject.AllTheSource;
import com.example.adrtravleproject.R;
import com.example.adrtravleproject.images.ImagesArea;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MapView;

public class Map extends Activity implements LocationListener, InfoWindowAdapter{

	public static Context mContext;
	private GoogleMap mMap;
	
	
	private MapView mv;
//	LocationManager locManager;
//	Location location;
//	Drawable marker;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.map);
		
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.setMyLocationEnabled(true);
		mMap.setTrafficEnabled(true);
		setupMapView();
		setUpMapIfNeeded();
		
		ImagesArea imgArea = AllTheSource.getInstance().getImagArea();
		ArrayList<ImagesArea> areaList = imgArea.imgAreaData;
		
		int idxOfList = 0;
		while(idxOfList < areaList.size())
		{
			ImagesArea ia = areaList.get(idxOfList);
			String mPath = ia.imgPathList.get(0); // 그 지역에서 찍힌 제일 첫번째 사진만 가지고 와서 path에 저장해 논
			LatLng mLoc = ia.getLoc();
			String ctr = ia.country;
			String state = ia.state;
			String local = ia.locality;
			String fullAddr = ia.fullAddr;
			
			mMap.addMarker(new MarkerOptions()
								.position(mLoc)
								.title("FirstTry")
								.snippet("does this work?")
									);
		}
	
		
		
	}

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	private void setupMapView(){
		 UiSettings settings = mMap.getUiSettings();        
		 mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
		  new CameraPosition(new LatLng(37.0, 126.5), 
		  7.5f, 30f, 112.5f))); // zoom, tilt, bearing
		 mMap.setTrafficEnabled(true);
		 settings.setAllGesturesEnabled(true);
		 settings.setCompassEnabled(true);
		 settings.setMyLocationButtonEnabled(true);
		 settings.setRotateGesturesEnabled(true);
		 settings.setScrollGesturesEnabled(true);
		 settings.setTiltGesturesEnabled(true);
		 settings.setZoomControlsEnabled(true);
		 settings.setZoomGesturesEnabled(true);
	}
	
	@SuppressLint("NewApi")
	private void setUpMapIfNeeded() {
	    // Do a null check to confirm that we have not already instantiated the map.
	    if (mMap == null) {
	        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	                            .getMap();
	        // Check if we were successful in obtaining the map.
	        if (mMap != null) {
	            // The Map is verified. It is now safe to manipulate the map.

	        }
	    }	    
	}

	public View getInfoContents(Marker mark) {
		// TODO Auto-generated method stub
		View v = getLayoutInflater().inflate(R.layout.customwindow, null);
		// Getting the position from the marker
        LatLng latLng = mark.getPosition();

        // Getting reference to the TextView to set latitude
        TextView tvLat = (TextView) v.findViewById(R.id.info_inner_title);
        // Setting the latitude
        tvLat.setText("Latitude:" + latLng.latitude);
       
        /**
         * 윈도안에 이미지 뷰 생성하고 뷰에 사진 띄어놓
         */
        ImageView photo = (ImageView) v.findViewById(R.id.photo);
        ImagesArea imgArea = AllTheSource.getInstance().getImagArea();
		ArrayList<ImagesArea> areaList = imgArea.imgAreaData;
		ImagesArea ia;
		final ArrayList uriList;
		
		int idxOfList = 0;
		while(idxOfList < areaList.size()) {
			ia = areaList.get(idxOfList);
			if(ia.getLoc().equals( latLng )) {
				String mPath = ia.imgPathList.get(0); // 그 지역에서 찍힌 제일 첫번째 사진만 가지고 와서 path에 저장해 논
				photo.setImageURI(Uri.parse(mPath));
//				uriList = ia.imgPathList;
			}
			
		}
		
		/**
		 * 이미지 클릭하면 그리드 뷰로~
		 */
        photo.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent intent = new Intent("android.intent.action.GRID_ALBUM");
//				intent.putStringArrayListExtra("uriList", uriList);
				/////// 여기서 Custom Album을 부르려는데 extend Activity를 못해서 context 데리고 함
//				context.startActivity(intent);	
			
			}
        });
        
        
        // Returning the view containing InfoWindow contents
        return v;
	}

	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
