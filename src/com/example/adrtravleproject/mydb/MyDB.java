package com.example.adrtravleproject.mydb;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MyDB {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_IMGID = "Img_ID";
	public static final String KEY_DATE = "Date";
	public static final String KEY_LATI = "Latitude";
	public static final String KEY_LONGI = "Longitude";
	public static final String KEY_MEMO = "Memo";
	
	private static final String TAG = "DbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
/*	
	private static final String DATABASE_CREATE = 
			"create table data(Img_ID integer primary key, " + "Date int not null, Latitude int, " +
					"Longitude int , Memo text )"; 
*/	
	private static final String DATABASE_CREATE = 
			"create table data (" +
			KEY_LATI + " INTEGER, " +
			KEY_LONGI + " INTEGER, " +
			KEY_DATE + " INTEGER, " +
			KEY_IMGID + " INTEGER PRIMARY KEY, " +
			KEY_MEMO + " TEXT) ";
	
	private static final String DATABASE_NAME = "TravleProj.db";
	private static final String DATABASE_TABLE = "data";
	private static final int DATABASE_VERSION = 1;
	
	private Context mCtx;
	
	private class DatabaseHelper extends SQLiteOpenHelper{

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub this is created only once
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub	
			Log.w(TAG, "Upgrading db from version" + oldVersion + " to" + 
			newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABEL IF EXISTS data");
			onCreate(db);
		}		
	}
	
	public MyDB(Context ctx){ /* constructor */
		this.mCtx = ctx;
	}
	
	public MyDB open() throws SQLException{ // dbopen할때마다 helper생성 ... 한번만 생성되는건가?
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		mDb.close();
		mDbHelper.close();
	}
	
	public long createRec(int img_id, int date, int lat, int longi, String memo){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_IMGID, img_id);
		initialValues.put(KEY_DATE, date);
		initialValues.put(KEY_LATI, lat);
		initialValues.put(KEY_LONGI, longi);
		initialValues.put(KEY_MEMO, memo);
		
		return mDb.insert(DATABASE_TABLE, null, initialValues);
	}
	
	public boolean deleteRec(long img_id){
		return mDb.delete(DATABASE_TABLE, KEY_IMGID + "=" + img_id, null) > 0;						
	}
	
	public void dropTable(){
		mDb.execSQL("DROP table data");		
	}
	
	public Cursor fetchAllRec(){
		return mDb.query(DATABASE_TABLE, new String[]
				{KEY_IMGID, KEY_DATE, KEY_LONGI, KEY_LATI, KEY_MEMO }, 
				null, null, null, null, null);
	}
	
	public Cursor fetchRec(int img_id) throws SQLException{
		Cursor mCursor = 
				mDb.query(DATABASE_TABLE, new String[]
						{KEY_IMGID, KEY_DATE, KEY_LONGI, KEY_LATI, KEY_MEMO }, 
						KEY_IMGID + "=" + img_id, null, null, null, null);
		if(mCursor != null)
			mCursor.moveToFirst();		
		return mCursor;
		
	}
	
	public boolean updateRec(){
		return false;		
	}



	

}
