package com.phonegap.luoo.plugin;

import java.io.File;
import java.io.IOException;

import com.aliyun.LuooFm.EntranceActivity;



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
			luooPlayerService = EntranceActivity.luooPlayerService;
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
			getPlayerService().startStreaming(url, destFile);			
		}
		
	}
	
}
