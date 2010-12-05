package com.a3.datastream;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.a3.datastream.LocationFinder.LocationResult;

public class PostDataActivity extends Activity {
	private LocationFinder locFinder;
	private DataStreamApp app;
	
	public LocationResult locResult = new LocationResult() {

		@Override
		public void gotLocation(Location location) {
			TextView upLocV = (TextView) findViewById(R.id.updateLocStatus);
			
			if(location != null) {
				app.curLoc = location;
			} else {
				upLocV.setText(R.string.locUnavailable);
			}
		}
		
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        app = (DataStreamApp) getApplicationContext();
        
        app.curLoc = new Location(LocationManager.NETWORK_PROVIDER);
        
        locFinder = new LocationFinder();
        locFinder.getLocation(this, locResult);
    }
    
    public void onUpdate(View target) {
    	Intent intent = new Intent(this, PostService.class);
    	intent.putExtra("text", ((EditText) findViewById(R.id.updateText)).getText().toString() );
    	startService( intent );
    }
    
    public void onAttach(View target) {
    	startActivity( new Intent(this, CameraActivity.class) );
    }
}