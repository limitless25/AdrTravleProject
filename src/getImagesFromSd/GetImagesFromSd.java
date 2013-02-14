package getImagesFromSd;

import android.database.Cursor;
import android.provider.MediaStore;

public class GetImagesFromSd{
	
	/**
	 * @param args
	 * @return 
	 */
	
	public static GetImagesFromSd GIFS;	

	public void initialize(){
		/** getting a photo from gallery by choosing images in person **/
        /*
        Intent photoPickIntent = new Intent(Intent.ACTION_GET_CONTENT);
	    photoPickIntent.setType("image/*");
	    startActivityForResult(photoPickIntent, SELECT_PHOTO);   
	    **/     	
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
