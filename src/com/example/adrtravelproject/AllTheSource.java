package com.example.adrtravelproject;

import java.util.ArrayList;

import android.content.Context;

import com.example.adrtravelproject.images.ImagesArea;
import com.example.adrtravelproject.mydb.MyDB;

public class AllTheSource {
	private static AllTheSource instance = null;
	private Context context;
	
	private MyDB myDB;
	private ImagesArea imgArea;
	
	private AllTheSource(){}
	
	public static void initialize(Context context){
		instance = new AllTheSource();
		instance.context = context;
		instance.myDB = new MyDB(context);	
		instance.imgArea = new ImagesArea(instance.context);
	}
	
	public static void close() {
		instance.myDB.close();
	}
	
	public static AllTheSource getInstance(){
		if (instance == null)
			throw new RuntimeException("call initialize plz");
		return instance;
	}
	public Context getContext() {
		return context;
	}
	public MyDB getDB(){
		return myDB;
	}
	public ImagesArea getImagArea(){
		return imgArea;
	}
}
