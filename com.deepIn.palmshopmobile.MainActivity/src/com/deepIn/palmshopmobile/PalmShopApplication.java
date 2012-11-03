package com.deepIn.palmshopmobile;

import com.taobao.top.android.TopAndroidClient;
import com.taobao.top.android.auth.AccessToken;

import android.app.Application;

public class PalmShopApplication extends Application {
	
	private AccessToken token;
	@Override  
	public void onCreate() {  
		super.onCreate();
		
		TopAndroidClient.registerAndroidClient(getApplicationContext(), "21148613", "38ea36fd0789eb4abaf8adf5c8fcdfc8", "callback://com.sample.inshop");
	}

    public  AccessToken getAccessToken()
    {
        return  token;
    }

    public  void  setAccessToken(AccessToken token)
   {
        this.token  =  token;
   }
}