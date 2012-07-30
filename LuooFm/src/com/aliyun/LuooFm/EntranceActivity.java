package com.aliyun.LuooFm;

import com.phonegap.DroidGap;

import android.annotation.SuppressLint;
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
	public LuooMediaPlayer luooPlayerService;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setFullscreen();   
		super.loadUrl("file:///android_asset/www/music-player/demo/index.html");
		appView.getSettings().setJavaScriptEnabled(true); 
		bindPlayerService();
		appView.addJavascriptInterface(new LuooFm(EntranceActivity.this), "LuooFm"); 
	}
	
	public LuooMediaPlayer getLuooMediaPlayer(){
		return this.luooPlayerService;
	}

	private ServiceConnection mServiceConnection = new ServiceConnection() {  
		public void onServiceConnected(ComponentName name, IBinder service) {  
			// TODO Auto-generated method stub  
			luooPlayerService = ((LuooMediaPlayer.LocalBinder)service).getService();  
		}  



		public void onServiceDisconnected(ComponentName name) {  
			// TODO Auto-generated method stub 
			//        		1luooPlayerService.onDestroy();
			Log.d("my", "disconnect service");
			mServiceConnection = null;
		}  
	};
	private Intent i;  
	public void bindPlayerService() {
		i  = new Intent();  
		i.setClass(this, LuooMediaPlayer.class);       
		// EntranceActivity.this.bindService(i, mServiceConnection, BIND_AUTO_CREATE);
		this.bindService(i, this.mServiceConnection, BIND_AUTO_CREATE);
	}
	public void setFullscreen() {
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  
	}

	@Override
	public void onDestroy () {
		Log.d("my", "opps");
		super.onDestroy();
		this.unbindService(mServiceConnection);
		this.stopService(new Intent().setClass(this, LuooMediaPlayer.class));
	}
}
