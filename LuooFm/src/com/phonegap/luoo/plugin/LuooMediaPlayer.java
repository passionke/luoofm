package com.phonegap.luoo.plugin;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;



import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;

public class LuooMediaPlayer extends Plugin {
	@Override
	public PluginResult execute(String action, JSONArray args, String callbackId) {
		// TODO Auto-generated method stub
		PluginResult.Status status = PluginResult.Status.OK;
		String result = "";
		if (action.equals("getVolPlayList")){
			try {
				result = HtmlGetter.getPlayList(args.getInt(0));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (action.equals("play")){			
			try {
				String playargs = args.getString(0);
				String url = playargs.split(",")[0];
				String vol = playargs.split(",")[1];
				LuooFm.play(url, vol);
				result = "ok~";
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (action.equals("pause")){
			LuooFm.pause();
		}else if (action.equals("resume")) {
			try {
				LuooFm.play();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (action.equals("getPlayingStatus")){
			result = LuooFm.getPlayingStatus();
		}else{
			status = PluginResult.Status.INVALID_ACTION;
		}
		return new PluginResult(status, result);
	}
}
