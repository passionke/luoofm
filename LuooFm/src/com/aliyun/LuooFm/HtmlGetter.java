package com.aliyun.LuooFm;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;

import android.util.Log;



public class HtmlGetter {	
	
	public HtmlGetter(){
		
	}
	public static void main(String[] args) throws IOException{
		new HtmlGetter().getPlayList();
	}
	public String getPlayList() throws IOException{
		Document doc = Jsoup.connect("http://luoo.net/").get();
		Element nowVol = doc.select("#sidebar li").first();
		Element li = nowVol.getElementsByTag("a").get(0);
		String vol = li.attr("href").replace("http://www.luoo.net/", "").replaceAll("/","");
		doc = Jsoup.connect(li.attr("href")).get();		
		Element post = doc.select(".post").get(0);
		Elements songsInfo = post.select(".post-txt p");
		String cover = songsInfo.get(0).getElementsByTag("img").get(0).attr("src");
		Log.d("my", cover);
		PlayList playList = new PlayList(cover, vol);
		Log.d("my", "length " + songsInfo.size());
		int  j = 0;
		for (int i = 3; i < songsInfo.size(); i = i+ 2){
			String poster = songsInfo.get(i).getElementsByTag("img").get(0).attr("src");
			Log.d("my", poster);
			String info = songsInfo.get(i + 1).text();
			Log.d("my", info);			
			String title = info.substring(0, info.indexOf("–"));
			Log.d("my", title);
			String airtist = info.substring(info.indexOf("–"), info.length());
			Log.d("my", airtist);
			j = j + 1;
			playList.appendSong( j, title, airtist, poster);
		}
		return playList.toString();
	}
}
