package com.a3.datastream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class CameraActivity extends Activity {
	
	CameraPreview preview;
	Button snapBtn;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.camera);
    	
    	preview = new CameraPreview(this);
    	((FrameLayout) findViewById(R.id.FramePreview)).addView(preview);
    	
    	snapBtn = (Button) findViewById(R.id.takePhoto);
    	snapBtn.setOnClickListener( snapClickListener );
    }
    
    OnClickListener snapClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			preview.camera.takePicture(shutterCallback, rawCallback, jpegCallback);
			
//			((CameraActivity) v.getParent()).photoTaken();
			v.setVisibility(View.INVISIBLE);
		}
	};
	
	OnClickListener uploadClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			((CameraActivity) getParent()).upload();
		}
		
	};
    
    // Called when shutter is opened
    ShutterCallback shutterCallback = new ShutterCallback() {
      public void onShutter() {
      }
    };

    // Handles data for raw picture
    PictureCallback rawCallback = new PictureCallback() { 
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
		}
    };

    // Handles data for jpeg picture
    PictureCallback jpegCallback = new PictureCallback() { 
      public void onPictureTaken(byte[] data, Camera camera) {
        FileOutputStream outStream = null;
        try {
          // Write to SD Card
          /*outStream = new FileOutputStream(String.format("/sdcard/%d.jpg",
              System.currentTimeMillis()));*/
        	
          outStream = new FileOutputStream("dssnapshot.jpg");
          outStream.write(data);
          outStream.close();
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
        }
      }
    };

	protected void photoTaken() {
		((FrameLayout) findViewById(R.id.FramePreview)).removeView(preview);
		
		snapBtn.setOnClickListener(uploadClickListener);
		snapBtn.setText(R.string.cameraPrevClick);
	}

	protected void upload() {
		//TO-DO: upload to dropbox
	}
	
	protected void onBack(View target) {
		finish();
	}
}
