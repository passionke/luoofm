package com.aliyun.LuooFm;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;


import android.util.Log;

public class PlayList {
	private String cover;
	private String vol;
	private ArrayList<PlayListItem> playList = new ArrayList<PlayListItem>();
	
	public PlayList(String cover, String vol){
		this.cover = cover;	
		this.vol = vol;
	}
	public void appendSong(int index, String title, String poster, String airtist){
		String musicUrl = new MusicUrl(vol, String.valueOf(index)).toString();
		Log.d("my", musicUrl);	
		playList.add(new PlayListItem(musicUrl, title, poster, airtist));
	}
	@Override
	public String toString() {
		String json = "{\"cover\":\"" + this.cover + "\",\"vol\":\"" + this.vol + "\",\"playList\":[" ;
		for (int i = 0; i < playList.size() - 1; i ++){
			json += (playList.get(i).toString() + ",");
		}
		json += playList.get(playList.size() - 1).toString();
		json += "]}";
		Log.d("my", json);
		return json;
	}
}
