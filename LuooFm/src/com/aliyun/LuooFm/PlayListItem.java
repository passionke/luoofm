package com.aliyun.LuooFm;



public class PlayListItem {
	private String mp3;
	private String title;
	private String cover;
	private String artist;
	
	public PlayListItem(String musicUrl, String title, String artist, String poster){
		this.mp3 = musicUrl;
		this.title = title;
		this.artist = artist;
		this.cover = poster;
	}
	
	public String toString(){
		String json = "{\"mp3\":\"" + this.mp3 + "\", \"title\":\"" + this.title + "\", \"artist\":\"" + this.artist + "\",\"cover\":\"" + this.cover + "\"}"; 
		return json;
	}
	
}
