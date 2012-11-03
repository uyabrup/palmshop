package com.deepIn.palmshopmobile;

import com.taobao.top.android.TopAndroidClient;

import android.app.Application;

public class PalmShopApplication extends Application {
	@Override  
	public void onCreate() {  
		super.onCreate();
		
		TopAndroidClient.registerAndroidClient(getApplicationContext(), "21148613", "38ea36fd0789eb4abaf8adf5c8fcdfc8", "callback://com.sample.inshop");
	}
}