package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ServiceTest extends Service{
	
	static final String TAG="LocalService";
	
	//onCreateの通過確認
    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        Toast.makeText(this, "MyService#onCreate", Toast.LENGTH_SHORT).show();
    }
 
    //onStartCommandの通過確認
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand Received start id " + startId + ": " + intent);
        Toast.makeText(this, "MyService#onStartCommand サービス接続", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }
    
    //onDestroyの通過確認
    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        Toast.makeText(this, "MyService#onDestroy サービス切断", Toast.LENGTH_SHORT).show();
    }
    
    //サービスに接続するためのBinder
    public class MyServiceLocalBinder extends Binder {
        //サービスの取得
        ServiceTest getService() {
            return ServiceTest.this;
        }
    }
    
    //Binderの生成
    private final IBinder mBinder = new MyServiceLocalBinder();
    
    //bind通過確認
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "MyService#onBind サービス接続"+ ": " + intent, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onBind" + ": " + intent);
        return mBinder;
    }
    
    //bindの再接続
    @Override
    public void onRebind(Intent intent){
        Toast.makeText(this, "MyService#onRebind サービス再接続"+ ": " + intent, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onRebind" + ": " + intent);
    }
    
    //bind切断確認
    @Override
    public boolean onUnbind(Intent intent){
        Toast.makeText(this, "MyService#onUnbind サービス切断"+ ": " + intent, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onUnbind" + ": " + intent);
        return true;
    }
        
}
