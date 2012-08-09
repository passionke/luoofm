package com.aliyun.LuooFm;

import com.phonegap.DroidGap;
import com.phonegap.luoo.plugin.LuooMediaPlayerService;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

@SuppressLint("SetJavaScriptEnabled")
public class EntranceActivity extends DroidGap {
	/** Called when the activity is first created. */
	public static LuooMediaPlayerService luooPlayerService;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setFullscreen(); 
		bindPlayerService();
		super.loadUrl("file:///android_asset/www/music-player/demo/index.html");
		//appView.getSettings().setJavaScriptEnabled(true); 
		
		//appView.addJavascriptInterface(new LuooFm(EntranceActivity.this), "LuooFm"); 
	}
	
	public static LuooMediaPlayerService getLuooMediaPlayer(){
		return luooPlayerService;
	}

	private ServiceConnection mServiceConnection = new ServiceConnection() {  
		public void onServiceConnected(ComponentName name, IBinder service) {  
			// TODO Auto-generated method stub  
			luooPlayerService = ((LuooMediaPlayerService.LocalBinder)service).getService();  
			Log.d("my", "i have init");
		}  
		public void onServiceDisconnected(ComponentName name) {  
			// TODO Auto-generated method stub 
			//        		1luooPlayerService.onDestroy();
			Log.d("my", "disconnect service");
			mServiceConnection = null;
		}  
	};
	  
	public void bindPlayerService() {
		Intent i = new Intent();  
		i.setClass(this, LuooMediaPlayerService.class);       
		// EntranceActivity.this.bindService(i, mServiceConnection, BIND_AUTO_CREATE);
		this.bindService(i, mServiceConnection, BIND_AUTO_CREATE);
	}
	public void setFullscreen() {
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  
	}

	@Override
	public void onDestroy () {
		Log.d("my", "opps");		
		this.unbindService(mServiceConnection);
		this.stopService(new Intent().setClass(this, LuooMediaPlayerService.class));
		super.onDestroy();
		
		//清除通知栏
		NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel(0);
		System.exit(0);
	}
}
