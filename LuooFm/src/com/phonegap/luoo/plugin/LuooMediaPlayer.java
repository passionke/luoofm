package com.phonegap.luoo.plugin;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.aliyun.LuooFm.HtmlGetter;
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
				result = new HtmlGetter().getPlayList(args.getInt(0));
				Log.d("my", "action!!  " + action);
				Log.d("my", "action!!  " + args);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			status = PluginResult.Status.INVALID_ACTION;
		}
		return new PluginResult(status, result);
	}

}
