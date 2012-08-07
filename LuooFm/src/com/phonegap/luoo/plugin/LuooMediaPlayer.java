package com.phonegap.luoo.plugin;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;



import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.aliyun.LuooFm.EntranceActivity;
import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;

public class LuooMediaPlayer extends Plugin {
	@Override
	public PluginResult execute(String action, JSONArray args, String callbackId) {
		// TODO Auto-generated method stub
		PluginResult.Status status = PluginResult.Status.OK;
		String result = "";
		if (action.equals("getVolPlayList")){
			try {
				result = HtmlGetter.getPlayList(args.getInt(0));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (action.equals("play")){		
			showNotification();
			try {
				String playargs = args.getString(0);
				String url = playargs.split(",")[0];
				String vol = playargs.split(",")[1];
				LuooFm.play(url, vol);
				result = "ok~";
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (action.equals("pause")){
			LuooFm.pause();
		}else if (action.equals("resume")) {
			try {
				LuooFm.play();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (action.equals("getPlayingStatus")){
			result = LuooFm.getPlayingStatus();
		}else if (action.equals("updatenotification")){
			
		}else{
			status = PluginResult.Status.INVALID_ACTION;
		}
		return new PluginResult(status, result);
	}
	
	private void showNotification() {
		 String ns = Context.NOTIFICATION_SERVICE;
		  NotificationManager mNotificationManager = (NotificationManager) this.ctx.getSystemService(ns);
		  long when = System.currentTimeMillis();
		  Notification notification = new Notification(R.drawable.btn_star, "总有一天我会离开你", when);
		  Context context = this.ctx.getApplicationContext();

        CharSequence contentTitle = "我的通知栏标展开标题";

        CharSequence contentText = "我的通知栏展开详细内容";

        Intent notificationIntent = new Intent(this.ctx, EntranceActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this.ctx, 0,notificationIntent, 0);

        notification.setLatestEventInfo(context, contentTitle, contentText,contentIntent); 

        //用mNotificationManager的notify方法通知用户生成标题栏消息通知

        mNotificationManager.notify(1, notification);
		  
	}
}
