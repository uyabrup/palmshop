package com.deepIn.palmshopmobile;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class LoginActivity extends Activity {
	
	final Activity activity = this;
	public void onCreate(Bundle savedInstanceState) {
	      
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palm_shop_main);
        
        // String strLogistic = taobao_logistics_companies_get();
         
         
         
         //WebView myWebView = (WebView) findViewById(R.id.webview);
         //myWebView.loadUrl("https://oauth.taobao.com/authorize?response_type=token&client_id=21148613&scope=item&state=1212&view=wap");
         
         WebView myWebView = (WebView) findViewById(R.id.webview);
         myWebView.getSettings().setJavaScriptEnabled(true);
         myWebView.setWebChromeClient(new WebChromeClient(){
         	public void onProgressChanged(WebView view, int progress)
         	{
				activity.setTitle("Loading....");
         		activity.setProgress(progress * 100);
         		  
                 if(progress == 100)
                     activity.setTitle(R.string.app_name);
         	}
         });
         
         myWebView.setWebViewClient(new WebViewClient() {
             public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
             {
                 // Handle the error
            	 
            	 String err = description;
            	 Log.e("aaa", err);
             }
   
             public boolean shouldOverrideUrlLoading(WebView view, String url)
             {
                 //view.loadUrl(url);
            	 Log.e("palmshop", url);
            	 
            	 Uri uri = Uri.parse(url);
            	 //List<String> accessToken1 = uri.getQueryParameter("refresh_token");
            	 List<String> accessToken1 = null;
            	 try {
					Map<String, List<String>> s =  getUrlParameters(url);
					accessToken1 = s.get("refresh_token");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	 if (accessToken1 != null)
            	 {
            		 String accessToken = accessToken1.get(0);
            		 if (accessToken != null)
                	 {
                		 Log.e("palmshop", accessToken);
                		 LoginActivity.this.finish();
                	 }
            	 }
            	 
            	 
            	 
            	 
            	 
                 return false;
             }
         });
         
         myWebView.loadUrl("https://oauth.taobao.com/authorize?response_type=token&client_id=21148613&scope=item&state=1212&view=wap");
        
        
    }
	
	public static Map<String, List<String>> getUrlParameters(String url)
	        throws UnsupportedEncodingException {
	    Map<String, List<String>> params = new HashMap<String, List<String>>();
	    String[] urlParts = url.split("\\?");
	    if (urlParts.length > 1) {
	        String query = urlParts[1];
	        for (String param : query.split("&")) {
	            String pair[] = param.split("=");
	            String key = URLDecoder.decode(pair[0], "UTF-8");
	            String value = "";
	            if (pair.length > 1) {
	                value = URLDecoder.decode(pair[1], "UTF-8");
	            }
	            List<String> values = params.get(key);
	            if (values == null) {
	                values = new ArrayList<String>();
	                params.put(key, values);
	            }
	            values.add(value);
	        }
	    }
	    return params;
	}

}
