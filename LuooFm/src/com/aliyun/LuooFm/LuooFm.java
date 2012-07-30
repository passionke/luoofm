package com.aliyun.LuooFm;

import java.io.File;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class LuooFm {
	public String Version = "1.0";
	public String Author = "passionke";
	private LuooMediaPlayer luooPlayerService;
	private EntranceActivity ctx;

	public LuooFm(EntranceActivity ctx) {
		// TODO Auto-generated constructor stub
		this.ctx = ctx;
	}
	
	public void pause () {
		this.luooPlayerService.pausePlayer();
	}
	public String getPlayingStatus() {
		if (this.luooPlayerService == null){
			this.luooPlayerService = ctx.getLuooMediaPlayer();
		}
		return this.luooPlayerService.getPlayingStatus();
	}

	public void play(String url, String vol) throws IOException {
		if (this.luooPlayerService == null){
			this.luooPlayerService = ctx.getLuooMediaPlayer();
		}
		Log.d("my", "start play " + url);
		String sdCardDir = Environment.getExternalStorageDirectory() + "/LuooFm/download/radio" + vol + "/";
		String fileEx = url.substring(url.lastIndexOf(".") + 1, url.length()).toLowerCase(); 
	    String fileNa = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")); 
		File destFile = new File(sdCardDir, fileNa + "." + fileEx);  
		if (destFile.exists()) {			
			this.luooPlayerService.playLocalMedia(destFile);
		}else{			
			this.luooPlayerService.startStreaming(url, destFile);
		}
		
	}
	
}
