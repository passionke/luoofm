package com.aliyun.LuooFm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import android.os.Environment;
import android.util.Log;

public class HtmlGetter {	
	
	private static Document luooDoc;
	public HtmlGetter() throws IOException{
		
	}
	
	private static String getPlayListLocaly(File file, String url, String vol) throws IOException {
		Log.d("my", "get playList locally");
		 BufferedReader reader = null;
		 StringBuffer strBuf = new StringBuffer();
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            while ((tempString = reader.readLine()) != null) {
	                strBuf.append(tempString);
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	    if (strBuf.toString().endsWith("}")){
	    		Log.d("my"," ok, send the play list");
	    		return strBuf.toString();
	    }else{
	    		Log.d("my", "opps, will get it from remote");
	    		return getPlayListRemote(url, file, vol);
	    }
		
	}
	
	public static String getPlayListRemote(String url, File playListFile, String vol) throws IOException {		
		Document doc = Jsoup.connect(url).timeout(30000).get();
		String volTitle = doc.select(".title a").get(0).text();
		Element post = doc.select(".post").get(0);
		Elements songsInfo = post.select(".post-txt p");
		String cover = songsInfo.get(0).getElementsByTag("img").get(0).attr("src");		
		PlayList playList = new PlayList(cover, vol, volTitle);		
		int  j = 0;
		// vol输出的网页中，有的可能会出现一个空的<p>，或者没有图片的p，处理的方式为：若当前p不包含img，则i+1，
		
		for (int i = 2; i < songsInfo.size(); i = i+ 2){
			Elements imgs = songsInfo.get(i).select("img");
			if (imgs.size() > 0){
				String poster = songsInfo.get(i).select("img").get(0).attr("src");
				String info = songsInfo.get(i + 1).text();
				String title = info.substring(0, info.indexOf("–"));
				String airtist = info.substring(info.indexOf("–") + 2, info.length());
				j = j + 1;
				playList.appendSong( j, title, airtist, poster);
			}else{
				i = i - 1;
			}			
		}
		//save list file to sdcard
		playListFile.getParentFile().mkdirs();
		playListFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(playListFile, true);
		fos.write(playList.toString().getBytes());
		fos.close();
		return playList.toString();
	}
	
	public static String getPlayList(int index) throws IOException{
		if (luooDoc == null) luooDoc = Jsoup.connect("http://www.luoo.net/").get();
		Document doc = luooDoc;
		Element nowVol = doc.select("#sidebar li:eq(" + index + ")").get(0);
		Element li = nowVol.getElementsByTag("a").get(0);
		String vol = li.attr("href").replace("http://www.luoo.net/", "").replaceAll("/","");
		String sdCardDir = Environment.getExternalStorageDirectory() + "/LuooFm/vol" + vol + "/";
		File playListFile = new File(sdCardDir + "playList.json");
		Log.d("my", "index   :" + li.attr("href"));
		if (playListFile.exists()){
			return getPlayListLocaly(playListFile, li.attr("href"), vol);
		}else{
			return getPlayListRemote(li.attr("href"), playListFile, vol);
		}		
	}
}
