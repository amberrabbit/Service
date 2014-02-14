package com.example.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        Button btn = (Button) findViewById(R.id.s_start);
        btn.setOnClickListener(btnListener);//リスナの登録
 
        btn  = (Button) findViewById(R.id.s_stop);
        btn.setOnClickListener(btnListener);//リスナの登録
        
        btn  = (Button) findViewById(R.id.b_start);
        btn.setOnClickListener(btnListener);//リスナの登録
        
        btn  = (Button) findViewById(R.id.b_stop);
        btn.setOnClickListener(btnListener);//リスナの登録
        
	}
	
	//ボタン用のリスナー
    private OnClickListener btnListener = new OnClickListener() {
        public void onClick(View v) {
 
            switch(v.getId()){
 
            case R.id.s_start://startServiceでサービスを起動
                startService(new Intent(MainActivity.this, ServiceTest.class));
                break;
                
            case R.id.s_stop://stopServiceでサービスの終了
                stopService(new Intent(MainActivity.this, ServiceTest.class));
                break;
                
            case R.id.b_start://bind開始
                doBindService();
                break;
     
            case R.id.b_stop://bind停止
                doUnbindService();
                break;
                
            }
        }
    };

    //取得したServiceの保存
    private ServiceTest mBoundService;
    private boolean mIsBound;
     
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
     
            // サービスとの接続確立時に呼び出される
            Toast.makeText(MainActivity.this, "bindでサービスと接続",
                    Toast.LENGTH_SHORT).show();
     
            // サービスにはIBinder経由で#getService()してダイレクトにアクセス可能
            mBoundService = ((ServiceTest.MyServiceLocalBinder)service).getService();
     
        }
     
        // サービスとの切断(異常系処理)
        // プロセスのクラッシュなど意図しないサービスの切断が発生した場合に呼ばれる。
        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
            Toast.makeText(MainActivity.this, "予期せずにbindでサービスの切断",
                    Toast.LENGTH_SHORT).show();
        }
    };

    //サービスとの接続を確立する。明示的にServiceを指定
    //(特定のサービスを指定する必要がある。他のアプリケーションから知ることができない = ローカルサービス)
    void doBindService() {
        bindService(new Intent(this,
                ServiceTest.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }
     
    //コネクションの解除
    void doUnbindService() {
        if (mIsBound) {
            unbindService(mConnection);
            mIsBound = false;
        }
    }
     
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
