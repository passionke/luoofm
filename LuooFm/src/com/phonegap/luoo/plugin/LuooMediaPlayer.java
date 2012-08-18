
package com.phonegap.luoo.plugin;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;




import android.util.Log;

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
				String[] playargs = args.getString(0).split(",");
				String url = playargs[0];
				String vol = playargs[1];
				String title = playargs[2];
				String artist =playargs[3];
				Log.d("my", "notitification " + title + "artist" + artist);
				LuooFm.play(url, vol);
				LuooFm.showNotification(this.ctx, vol, title + " - " + artist);
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
		}else if (action.equals("updatenotification")){
			
		}else{
			status = PluginResult.Status.INVALID_ACTION;
		}
		return new PluginResult(status, result);
	}

	/* (non-Javadoc)
	 * @see com.phonegap.api.Plugin#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
}