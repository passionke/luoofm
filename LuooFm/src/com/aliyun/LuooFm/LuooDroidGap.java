package com.aliyun.LuooFm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;

import com.phonegap.DroidGap;

public class LuooDroidGap extends DroidGap {
	public LuooDroidGap(){
		super();
	}
	@Override
	public void loadUrl(String url){		
		String fileEx = url.substring(url.lastIndexOf(".") + 1, url.length()).toLowerCase(); 
		if (fileEx == "mp3"){	
			Log.d("my", "I get it " + url);
		}
		Log.d("my", "I get it " + url);
		super.loadUrl(url);
	}

	 private void getFile(final String strPath)   
	    {   
	      try  
	      {   
	        Runnable r = new Runnable()   
	        {   
	          public void run()   
	          {   
	            try  
	            {   
	              // 开启一个线程进行远程文件下载   
	              getDataSource(strPath);   
	            } catch (Exception e)   
	            {   
	              Log.e(TAG, e.getMessage(), e);   
	            }   
	          }   
	        };   
	        new Thread(r).start();   
	      } catch (Exception e)   
	      {   
	        e.printStackTrace();   
	      }   
	    }   
	private void getDataSource(String strPath) throws Exception   
	{   
		String fileEx = strPath.substring(strPath.lastIndexOf(".") + 1, strPath.length()).toLowerCase(); 
        System.out.println("***************fileEx =" + fileEx);   
        String fileNa = strPath.substring(strPath.lastIndexOf("/") + 1, strPath.lastIndexOf(".")); 
		if (!URLUtil.isNetworkUrl(strPath))   
		{   
			System.out.println("错误的URL");   
		} else  
		{   
			/* 取得URL */  
			URL myURL = new URL(strPath);   
			/* 创建连接 */  
			URLConnection conn = myURL.openConnection();   
			conn.connect();   
			/* InputStream 下载文件 */  
			InputStream is = conn.getInputStream();   
			if (is == null)   
			{   
				throw new RuntimeException("stream is null");   
			}   
			// 创建临时文件    
			// 两个参数分别为前缀和后缀
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				File sdCardDir = Environment.getExternalStorageDirectory();
				File saveFile = new File(sdCardDir, "/LuooFm/download" + fileNa + "." + fileEx);  
				saveFile.getAbsolutePath();   
				/* 将文件写入暂存盘 */  
				FileOutputStream fos = new FileOutputStream(saveFile);   
				byte buf[] = new byte[128];   
				do  
				{   
					int numread = is.read(buf);   
					if (numread <= 0)   
					{   
						break;   
					}   
					fos.write(buf, 0, numread);   
				} while (true); 
			}
		}   
	}  
}
