package com.a3.datastream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PostService extends Service {

	HttpHelper httpHelper;
	private DataStreamApp app;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		httpHelper = new HttpHelper();
		app = (DataStreamApp) getApplicationContext();
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		List<NameValuePair> listVal = new ArrayList<NameValuePair>();
		listVal.add( new BasicNameValuePair("handler", "userHandler") );
		listVal.add( new BasicNameValuePair("text", intent.getStringExtra("text")) );
		listVal.add( new BasicNameValuePair("lat", Double.toString(app.curLoc.getLatitude())) );
		listVal.add( new BasicNameValuePair("lon", Double.toString(app.curLoc.getLongitude())) );
		listVal.add( new BasicNameValuePair("timestamp", Double.toString(new Date().getTime())) );
		listVal.add( new BasicNameValuePair("category", "kategori1") );
		listVal.add( new BasicNameValuePair("imgpath", "path") );
		
		//httpHelper.sendPost("http://192.168.1.135:8989/post", listVal, null);
		//httpHelper.sendPost("http://192.168.70.175/rhok2jakarta/index.php/ajax/postdatamap", listVal, null);
		httpHelper.sendPost("http://rhokds.appspot.com/post", listVal, null);
	}
	
	@Override
	public void onDestroy() {
		
	}
}
