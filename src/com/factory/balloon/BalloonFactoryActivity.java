package com.factory.balloon;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class BalloonFactoryActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
      //full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        getWindow().setFormat(PixelFormat.RGB_565);
        
        //get device metrics
        DisplayMetrics displaymetrics = new DisplayMetrics(); 
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics); 
        int height = displaymetrics.heightPixels; 
        int width = displaymetrics.widthPixels;
        float h_factor = height/800.0f;
        float w_factor = width/480.0f;
        
        setContentView(new GameView(this, w_factor, h_factor));
    }
    
    protected void onStop() 
    {
        super.onStop();
        
        
        Log.d("GameActivity", "MYonStop is called");
        
        finish();
    }
    
    
    protected void onPause() {
    	super.onPause();
//    	if(view != null){
//    		view.releaseSounds();
//    	}
   	Log.d("GameActivity", "MYonPause is called");
//    	saveState();
//    	resuming=true; //so that on resume you can read in last state
	}
	
	
	protected void onResume() {
        super.onResume();
//        if(view != null){
//        	view.prepareSounds();
//        }
        Log.d("GameActivity", "!jestem w GameActivity.onResume()");
//        if (resuming) { //only if the game is being resumed, o/w the game that's sitting in the save file is not relevant
//        	readSavedState();
//        	Log.d("GameActivity", "read last saved state");
//            
        }
		
    
    
}