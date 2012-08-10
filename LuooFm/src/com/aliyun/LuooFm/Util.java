package com.aliyun.LuooFm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.NetworkInfo.State;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class Util {
	private static final String TAG = "Util";
	
	public static Context ctx;
	public static final String UPDATE_SERVER = "http://182.148.43.8/";
	public static final String UPDATE_APKNAME = "LuooFm.apk";
	public static final String UPDATE_VERJSON = "ver.json";
	public static final String UPDATE_SAVENAME = "LuooFm.apk";
	//
	
	public static ProgressDialog pBar;
	private static Handler handler = new Handler();
	
	private static int newVerCode = 0;
	private static String newVerName = "";
	public static int notification_id =19172439;
	public static NotificationManager nm;
	public static Notification n;
	public static PendingIntent contentIntent;
	//
	public static void Init(Context context)
	{
		ctx = context;
		/** add network check*/
		
		if(checkNetworkInfo()) {
			checkNewVersion();
		} else {
			
		}
	}
	public static void Exit()
	{	
	
	}
	public static int getVerCode() {
		int verCode = -1;
		try {
			verCode = ctx.getPackageManager().getPackageInfo(
					"com.aliyun.LuooFm", 0).versionCode;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return verCode;
	}
	
	public static String getVerName() {
		String verName = "";
		try {
			verName = ctx.getPackageManager().getPackageInfo(
					"com.aliyun.LuooFm", 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return verName;	

	}
	
	public static String getAppName() {
		String verName = ctx.getResources().getText(R.string.app_name).toString();
		return verName;
	}	
    private static boolean checkNetworkInfo()
    {
    	ConnectivityManager conMan = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
    	State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
    	if(wifi != State.CONNECTED ){
        	Toast toast=Toast.makeText(ctx.getApplicationContext(), "WiFi网络未开启", Toast.LENGTH_SHORT);   
            toast.show();    
            return false;
    	}
    	return true;
    }
    /*
     * (non-Javadoc)
     * @see com.phonegap.DroidGap#onDestroy()
     */
    private static void checkNewVersion()
    {
		if (getServerVerCode()) {
			int vercode = getVerCode();
			if (newVerCode > vercode) {
				Log.d(TAG, "do new ver");
				doNewVersionUpdate();
			} else {
				Log.d(TAG, "no do new");
				notNewVersionShow();
			}
		} 
    	
    }
	private static void initNotification() {
		
		nm = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);   
		Intent notificationIntent = new Intent(ctx, ctx.getClass());
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		 contentIntent = PendingIntent.getActivity(ctx, 0, 
			notificationIntent, 0);

		
		n = new Notification(R.drawable.ic_launcher, "LuooFm", System.currentTimeMillis());              
		n.flags = Notification.FLAG_NO_CLEAR;  
		n.setLatestEventInfo(ctx,"LuoFm","",contentIntent);
		nm.notify(notification_id, n);
	}
	public void modifyNotification(String vol, String index, String song){
		n.setLatestEventInfo(ctx,"LuoFm"+" -"+vol,index+"-"+song,contentIntent);
	}
	private static boolean getServerVerCode() {
		try {
			String verjson = NetworkTool.getContent(UPDATE_SERVER
					+ UPDATE_VERJSON);
			JSONArray array = new JSONArray(verjson);
			if (array.length() > 0) {
				JSONObject obj = array.getJSONObject(0);
				try {
					newVerCode = Integer.parseInt(obj.getString("verCode"));
					newVerName = obj.getString("verName");
				} catch (Exception e) {
					newVerCode = -1;
					newVerName = "";
					return false;
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return false;
		}
		return true;
	}

	private static void notNewVersionShow() {
		int verCode = getVerCode();
		String verName = getVerName();
		StringBuffer sb = new StringBuffer();
		sb.append("Current Version:");
		sb.append(verName);
		sb.append(" Code:");
		sb.append(verCode);
		sb.append(",\nAlready is new!");
		Dialog dialog = new AlertDialog.Builder(ctx)
				.setTitle("Update").setMessage(sb.toString())// 鐠佸墽鐤嗛崘鍛啇
				.setPositiveButton("OK",// 鐠佸墽鐤嗙涵顔肩暰閹稿鎸�
						new DialogInterface.OnClickListener() {

							//@Override
							public void onClick(DialogInterface dialog,
									int which) {
								//finish();
							}

						}).create();// 閸掓稑缂�
		// 閺勫墽銇氱�纭呯樈濡楋拷		
		dialog.show();
	}

	private static void doNewVersionUpdate( ) {
		int verCode = getVerCode();
		String verName = getVerName();
		StringBuffer sb = new StringBuffer();
		sb.append("Current Version:");
		sb.append(verName);
		sb.append(" Code:");
		sb.append(verCode);
		sb.append(",Found New Version");
		sb.append(newVerName);
		sb.append(" Code:");
		sb.append(newVerCode);
		sb.append(",Are you sure to update?");
		Dialog dialog = new AlertDialog.Builder(ctx)
				.setTitle("Software Update")
				.setMessage(sb.toString())
			
				.setPositiveButton("Update",
						new DialogInterface.OnClickListener() {

							//@Override
							public void onClick(DialogInterface dialog,
									int which) {
								pBar = new ProgressDialog(ctx);
								pBar.setTitle("downloading...");
								pBar.setMessage("wait...");
								pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
								downFile(UPDATE_SERVER
										+ UPDATE_APKNAME);
							}

						})
				.setNegativeButton("Later",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								
								//finish();
							}
						}).create();
				
		dialog.show();
	}

	static void downFile(final String url) {
		pBar.show();
		new Thread() {
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					if (is != null) {

						File file = new File(
								Environment.getExternalStorageDirectory(),
								UPDATE_SAVENAME);
						fileOutputStream = new FileOutputStream(file);

						byte[] buf = new byte[1024];
						int ch = -1;
						//int count = 0;
						while ((ch = is.read(buf)) != -1) {
							fileOutputStream.write(buf, 0, ch);
							//count += ch;
							if (length > 0) {
							}
						}

					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					down();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}.start();

	}
	static void down() {
		handler.post(new Runnable() {
			public void run() {
				pBar.cancel();
				update();
			}
		});

	}

	static void update() {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), UPDATE_SAVENAME)),
				"application/vnd.android.package-archive");
		ctx.startActivity(intent);
	}
}
