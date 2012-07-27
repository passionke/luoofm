package com.aliyun.LuooFm;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class LuooFm {
	public String Version = "1.0";
	public String Author = "passionke";
	private LuooMediaPlayer luooPlayerService;

	public LuooFm(EntranceActivity ctx) {
		// TODO Auto-generated constructor stub
		this.luooPlayerService = ctx.luooPlayerService;
	}
	
	public String getPlayList() throws IOException {
		return new HtmlGetter().getPlayList();
	}

	public void play(String url, String vol) throws IOException {
		Log.d("my", "StartPlayA");
		Log.d("my", "Hahaha "  + this.luooPlayerService.getSystemTime());
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
