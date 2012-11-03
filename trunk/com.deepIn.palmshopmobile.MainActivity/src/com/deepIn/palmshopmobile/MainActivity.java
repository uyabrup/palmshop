package com.deepIn.palmshopmobile;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

    private String[] strArrays;



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
                startActivity(ins); 
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

}
