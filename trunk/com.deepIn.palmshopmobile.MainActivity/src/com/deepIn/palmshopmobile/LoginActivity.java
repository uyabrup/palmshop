package com.deepIn.palmshopmobile;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.taobao.top.android.TOPUtils;
import com.taobao.top.android.TopAndroidClient;
import com.taobao.top.android.auth.AccessToken;
import com.taobao.top.android.auth.AuthError;
import com.taobao.top.android.auth.AuthException;
import com.taobao.top.android.auth.AuthorizeListener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class LoginActivity extends Activity {
	
	final Activity activity = this;
	private TopAndroidClient client=TopAndroidClient.getAndroidClientByAppKey("21148613");
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
            	 
//            	 Uri uri = Uri.parse(url);
//            	 //List<String> accessToken1 = uri.getQueryParameter("refresh_token");
//            	 List<String> accessToken1 = null;
//            	 try {
//					Map<String, List<String>> s =  getUrlParameters(url);
//					accessToken1 = s.get("refresh_token");
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//            	 if (accessToken1 != null)
//            	 {
//            		 String accessToken = accessToken1.get(0);
//            		 if (accessToken != null)
//                	 {
//                		 Log.e("palmshop", accessToken);
//                		 LoginActivity.this.finish();
//                	 }
//            	 }
            	 
            	 
            	 saveAccessToken(url);
            	 
            	 
            	 
            	 
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
	
	protected TopAndroidClient getTopAndroidClient() {
		return client;
	}
	
	public void saveAccessToken(String url)
	{
		Uri uri =  Uri.parse(url);

		final TopAndroidClient client = getTopAndroidClient();
		//Uri u = Uri.parse(client.getRedirectURI());
//		if (uri != null && uri.getScheme().equals(u.getScheme())
//				&& uri.getHost().equals(u.getHost())
//				&& uri.getPort() == u.getPort()
//				&& uri.getPath().equals(u.getPath())) 
		String paramStr = uri.getFragment();
		if (paramStr != null)
		{

			String errorStr = uri.getQueryParameter("error");
			//AuthorizeListener listener = getAuthorizeListener();
			if (errorStr == null) {// 鎺堟潈鎴愬姛
			// String ret = url.substring(url.indexOf("#") + 1);
				String ret = uri.getFragment();
				String[] kv = ret.split("&");
				Bundle values = new Bundle();
				for (String each : kv) {
					String[] ss = each.split("=");
					if (ss != null && ss.length == 2) {
						values.putString(ss[0], ss[1]);
					}
				}
				final AccessToken token = TOPUtils.convertToAccessToken(values);
				// Android3.0鍚巙i涓荤嚎绋嬩腑鍚屾璁块棶缃戠粶浼氭湁闄愬埗銆�
				// 浣跨敤ExecutorService.invokeAll()闃诲涓荤嚎绋嬬殑鏂瑰紡璧蜂竴涓嚎绋嬪啀鍘昏皟鐢╝pi
				Callable<Date> task = new Callable<Date>() {
					@Override
					public Date call() throws Exception {
						Date date = client.getTime();
						return date;
					}
				};
				List<Callable<Date>> tasks = new ArrayList<Callable<Date>>();
				tasks.add(task);
				ExecutorService es = Executors.newSingleThreadExecutor();
				try {
					List<Future<Date>> results = es.invokeAll(tasks);
					Future<Date> future = results.get(0);
					token.setStartDate(future.get());
					
					client.addAccessToken(token);
					PalmShopApplication myApp  =  (PalmShopApplication)getApplication();
					myApp.setAccessToken(token);
				} catch (Exception e) {
					//listener.onAuthException(new AuthException(e));
				}				
				//listener.onComplete(token);
				//将intent传会上一个Activity
				Intent intent = this.getIntent();
				LoginActivity.this.setResult(RESULT_OK, intent);
				LoginActivity.this.finish();
			} else {// 鎺堟潈澶辫触
				String errorDes = uri.getQueryParameter("error_description");
				AuthError error = new AuthError();
				error.setError(errorStr);
				error.setErrorDescription(errorDes);
				//listener.onError(error);
			}
		}
	}

}
