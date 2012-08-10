<<<<<<< HEAD
package com.phonegap.luoo.plugin;

import java.io.File;
import java.io.IOException;

import com.aliyun.LuooFm.EntranceActivity;
import com.aliyun.LuooFm.R;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import android.util.Log;

public class LuooFm {
	public String Version = "1.0";
	public String Author = "passionke";
	private static LuooMediaPlayerService luooPlayerService;


	public LuooFm(LuooMediaPlayerService luooMediaPlayerService) {
		// TODO Auto-generated constructor stub
		//this.ctx = ctx;
		luooPlayerService = luooMediaPlayerService;
	}
	
	
	private static LuooMediaPlayerService getPlayerService() {
		if (luooPlayerService == null){
			luooPlayerService = EntranceActivity.getLuooMediaPlayer();
		}
		return luooPlayerService;
	}
	
	public static void pause () {
		getPlayerService().pausePlayer();
	}
	public static String getPlayingStatus() {		
		return getPlayerService().getPlayingStatus();
	}
	public static void play() throws Exception {
		getPlayerService().play();
	}
	public static void play(String url, String vol) throws IOException {		
		Log.d("my", "start play " + url);
		String sdCardDir = Environment.getExternalStorageDirectory() + "/LuooFm/download/radio" + vol + "/";
		String fileEx = url.substring(url.lastIndexOf(".") + 1, url.length()).toLowerCase(); 
	    String fileNa = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")); 
		File destFile = new File(sdCardDir, fileNa + "." + fileEx);  
		if (destFile.exists()) {
			Log.d("my", "play locally");
			getPlayerService().playLocalMedia(destFile, 0);
		}else{			
			Log.d("my", "play at remote");
			getPlayerService().setNewDownload(url);
			Log.d("my", "set last download as " + url);
			getPlayerService().startStreaming(url, destFile);			
		}
		
	}
	
	public static void showNotification(Context ctx, String title, String msg) {
		 String ns = Context.NOTIFICATION_SERVICE;
		  NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(ns);
		  long when = System.currentTimeMillis();
		  Notification notification = new Notification(R.drawable.icon, "updater", when);
		 notification.flags = notification.flags | Notification.FLAG_AUTO_CANCEL;
		  Context context = ctx.getApplicationContext();

       CharSequence contentTitle = "LuooFm" + title;
       CharSequence contentText = msg;
       Intent notificationIntent = new Intent(ctx, ctx.getClass());

       PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, notificationIntent, 0);

       notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent); 
       mNotificationManager.notify(0, notification);
		  
	}


	
	
}
=======
package com.phonegap.luoo.plugin;

import java.io.File;
import java.io.IOException;

import com.aliyun.LuooFm.EntranceActivity;
import com.aliyun.LuooFm.R;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import android.util.Log;

public class LuooFm {
	public String Version = "1.0";
	public String Author = "passionke";
	private static LuooMediaPlayerService luooPlayerService;


	public LuooFm(LuooMediaPlayerService luooMediaPlayerService) {
		// TODO Auto-generated constructor stub
		//this.ctx = ctx;
		luooPlayerService = luooMediaPlayerService;
	}
	
	
	private static LuooMediaPlayerService getPlayerService() {
		if (luooPlayerService == null){
			luooPlayerService = EntranceActivity.getLuooMediaPlayer();
		}
		return luooPlayerService;
	}
	
	public static void pause () {
		getPlayerService().pausePlayer();
	}
	public static String getPlayingStatus() {		
		return getPlayerService().getPlayingStatus();
	}
	public static void play() throws Exception {
		getPlayerService().play();
	}
	public static void play(String url, String vol) throws IOException {		
		Log.d("my", "start play " + url);
		String sdCardDir = Environment.getExternalStorageDirectory() + "/LuooFm/download/radio" + vol + "/";
		String fileEx = url.substring(url.lastIndexOf(".") + 1, url.length()).toLowerCase(); 
	    String fileNa = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")); 
		File destFile = new File(sdCardDir, fileNa + "." + fileEx);  
		if (destFile.exists()) {
			Log.d("my", "play locally");
			getPlayerService().playLocalMedia(destFile, 0);
		}else{			
			Log.d("my", "play at remote");
			getPlayerService().setNewDownload(url);
			Log.d("my", "set last download as " + url);
			getPlayerService().startStreaming(url, destFile);			
		}
		
	}
	/**
	 * modify by weibi for notification add vol 2012.8.7
	 */
	public static void showNotification(Context ctx, String vol, String msg) {
		 String ns = Context.NOTIFICATION_SERVICE;
		  NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(ns);
		  long when = System.currentTimeMillis();
		  Notification notification = new Notification(R.drawable.icon, "updater", when);
		 
		  Context context = ctx.getApplicationContext();

       CharSequence contentTitle = "LuooFm";

       CharSequence contentText = msg;

       Intent notificationIntent = new Intent(ctx, ctx.getClass());

       PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, notificationIntent, 0);

       notification.setLatestEventInfo(context, contentTitle+"  -VOL"+vol, contentText, contentIntent); 
       mNotificationManager.notify(1, notification);
		  
	}


	
	
}
>>>>>>> merge frome weibi luoofm-1
