package com.aliyun.LuooFm;

import java.util.ArrayList;


public class PlayList {
	private String cover;
	private String vol;
	private String title;
	private ArrayList<PlayListItem> playList = new ArrayList<PlayListItem>();
	
	public PlayList(String cover, String vol, String title){
		this.cover = cover;	
		this.vol = vol;
		this.title = title;
	}
	public void appendSong(int index, String title, String poster, String airtist){
		String musicUrl = new MusicUrl(vol, String.valueOf(index)).toString();
		playList.add(new PlayListItem(musicUrl, title, poster, airtist));
	}
	@Override
	public String toString() {
		String json = "{\"title\":\"" + this.title + "\",\"cover\":\"" + this.cover + "\",\"vol\":\"" + this.vol + "\",\"playList\":[" ;
		for (int i = 0; i < playList.size() - 1; i ++){
			json += (playList.get(i).toString() + ",");
		}
		json += playList.get(playList.size() - 1).toString();
		json += "]}";
		return json;
	}
}
