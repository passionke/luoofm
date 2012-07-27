package com.aliyun.LuooFm;

public class MusicUrl {
	private String vol;
	private String index;
	public MusicUrl(String vol, String index){
		this.vol = vol;
		this.index =  Integer.parseInt(index)<10?"0"+index:index;
	}
	public String toString(){
		return "http://ftp.luoo.net/radio/radio" + vol + "/" + index + ".mp3"; 
	}
}
