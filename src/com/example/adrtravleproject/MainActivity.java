package com.example.adrtravleproject;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;

public class MainActivity extends Activity{
	
	private Cursor mImageCursor;

    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);     
               
        /** getting a photo from gallery by choosing images in person **/
        /*
        Intent photoPickIntent = new Intent(Intent.ACTION_GET_CONTENT);
	    photoPickIntent.setType("image/*");
	    startActivityForResult(photoPickIntent, SELECT_PHOTO);   
	    **/         
        
        /** getting info of images from SD card by using MediaStore contentprovider **/
    	String[] proj = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.TITLE };
    	mImageCursor = managedQuery( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);
    	mImageCursor.moveToFirst();
    	
    	int columIndexID = mImageCursor.getColumnIndex(MediaStore.Images.Media.TITLE);
    	String title = mImageCursor.getString(columIndexID);
    	mImageCursor.moveToNext();
    	String title1 = mImageCursor.getString(columIndexID);

    	mImageCursor.close();
    }
    
    /*
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK)
		{
			Uri chosenImageUri = data.getData();	
			try {
				InputStream imageStream = getContentResolver().openInputStream(chosenImageUri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}		
	}*/

  
}
