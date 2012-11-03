package com.deepIn.palmshopmobile;

import java.util.ArrayList;
import java.util.HashMap;

import com.taobao.top.android.auth.AccessToken;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private String[] strArrays;
    private static final String LOG_TAG="PalmShop";


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        Button loginButton = (Button) findViewById(R.id.button_login);
        
        loginButton.setOnClickListener(new Button.OnClickListener(){ 
            public void onClick(View v)
            {
            	/*Toast.makeText(MainActivity.this,
                        "点击登录", 
                        Toast.LENGTH_LONG).show();*/
            	
            	Intent ins=new Intent(MainActivity.this,LoginActivity.class);
                //startActivity(ins); 
               
				//通过Bundle来获取数据,通过key-Value的方式放入数据
				Bundle bl = new Bundle();
				bl.putDouble("height", 5000);
				bl.putString("sex", "male");
				//将Bundle放入Intent传入下一个Activity
				ins.putExtras(bl);
				//跳到下一个Activity,并且等待其返回结果
				startActivityForResult(ins, 0);
				//不能够在这个Activity调用了startActivityForResult之后调用finsh()
				//否则无法接收到返回
            }
        });
         
        
        GridView gridview = (GridView) findViewById(R.id.gridView_home);
        
        ArrayList<Object> listImg = new ArrayList<Object>();
        listImg.add(R.drawable.baobeimanager);
        listImg.add(R.drawable.ordermanager);
        listImg.add(R.drawable.sendgoods);
        listImg.add(R.drawable.searchwuliu);
        listImg.add(R.drawable.opinion);
        listImg.add(R.drawable.wangwang);
        
        
        
        strArrays = new String[] {getResources().getString(R.string.baobeiMgr),
        		getResources().getString(R.string.tradeMgr),
        		getResources().getString(R.string.deliveryMgr),
        		getResources().getString(R.string.logisticMgr),
        		getResources().getString(R.string.commentMgr),
        		getResources().getString(R.string.wangwang)};
        
      //��ɶ�̬���飬����ת�����  
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();  
        for(int i=0;i<6;i++)  
        {  
          HashMap<String, Object> map = new HashMap<String, Object>();  
          map.put("ItemImage", listImg.get(i));//���ͼ����Դ��ID  
      map.put("ItemText", strArrays[i]);//�������ItemText  
          lstImageItem.add(map);  
        }  
        //�����������ImageItem <====> ��̬�����Ԫ�أ�����һһ��Ӧ  
        SimpleAdapter saImageItems = new SimpleAdapter(this, //ûʲô����  
                                                  lstImageItem,//�����Դ   
                                                  R.layout.gridview_item,//night_item��XMLʵ��  
                                                    
                                                  //��̬������ImageItem��Ӧ������          
                                                  new String[] {"ItemImage","ItemText"},   
                                                    
                                                  //ImageItem��XML�ļ������һ��ImageView,����TextView ID  
                                                  new int[] {R.id.imageView_ItemImage,R.id.textView_ItemText});  
        //��Ӳ�����ʾ  
        gridview.setAdapter(saImageItems);  
        //�����Ϣ����  
        //gridview.setOnItemClickListener(new ItemClickListener());  
    }
    
 

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (resultCode)
		{
			//结果返回
		case RESULT_OK:
			//获取Bundle的数据
			Bundle bl= data.getExtras();
			String sex=bl.getString("sex");
			
			Log.e(LOG_TAG, sex);
			PalmShopApplication myApp  =  (PalmShopApplication)getApplication();
			AccessToken token = myApp.getAccessToken();
			String id=token.getAdditionalInformation().get(AccessToken.KEY_SUB_TAOBAO_USER_NICK);
			if(id==null){
				id=token.getAdditionalInformation().get(AccessToken.KEY_TAOBAO_USER_NICK);
			}
			
			TextView text = (TextView) findViewById(R.id.textView_home_top);
			
			text.setText(id);
			
			
			break;
		default:
			break;
		}
	}

}
