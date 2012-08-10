<<<<<<< HEAD
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
		
		//æ¸…é™¤é€šçŸ¥æ 
		NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel(0);
		System.exit(0);
	}
}
=======
package com.aliyun.LuooFm;

import com.phonegap.DroidGap;
import com.phonegap.luoo.plugin.LuooMediaPlayerService;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled")
public class EntranceActivity extends DroidGap {
	/** Called when the activity is first created. */
	public static LuooMediaPlayerService luooPlayerService;
	private long touchTime = 0;  
	TelephonyManager manager ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setFullscreen(); 
		bindPlayerService();
		super.loadUrl("file:///android_asset/www/music-player/demo/index.html");
		//appView.getSettings().setJavaScriptEnabled(true); 
		
		//appView.addJavascriptInterface(new LuooFm(EntranceActivity.this), "LuooFm"); 
		//»ñÈ¡µç»°·þÎñ

        manager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);

        // ÊÖ¶¯×¢²á¶ÔPhoneStateListenerÖÐµÄlisten_call_state×´Ì¬½øÐÐ¼àÌý
        manager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);

        

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
		try{
		boolean result = this.stopService(new Intent().setClass(this, LuooMediaPlayerService.class));
		/**
		 * cancel notification
		 */
		if(!result){
			Log.e("my", "service stop error");
		}
		}catch(SecurityException  e) {
			e.printStackTrace();
		}
		NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(1);
		super.onDestroy();
		
	}

	/* (non-Javadoc)
	 * @see com.phonegap.DroidGap#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if((keyCode == KeyEvent.KEYCODE_BACK)&&(event.getAction() == KeyEvent.ACTION_DOWN) ){
			long currentTime = System.currentTimeMillis();  
	        if((currentTime-touchTime)>= 2000) {  
	            Toast.makeText(this, "ÔÙ°´Ò»´ÎÍË³ö", Toast.LENGTH_SHORT).show();  
	            touchTime = currentTime; 
	        } else {
	        	finish(); 
	        }   
	        return true; 
		} 
			
		return super.onKeyDown(keyCode, event);
	}
	class MyPhoneStateListener extends PhoneStateListener{

	    @Override
	    public void onCallStateChanged(int state, String incomingNumber) {

	        switch (state) {

	        case TelephonyManager.CALL_STATE_IDLE:
	        	if(luooPlayerService != null)
	        	luooPlayerService.isstop = false;
	            break;
	        case TelephonyManager.CALL_STATE_RINGING:
	        	if(luooPlayerService != null)
	        	luooPlayerService.isstop = true;
	            break;

	        case TelephonyManager.CALL_STATE_OFFHOOK:
	        	if(luooPlayerService != null)
	        	luooPlayerService.isstop = false;
	        	break;
	        default:

	            break;
	        }
	        super.onCallStateChanged(state, incomingNumber);

	    }

	    

	}

}

>>>>>>> merge frome weibi luoofm-1
