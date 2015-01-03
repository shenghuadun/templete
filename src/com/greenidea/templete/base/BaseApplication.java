package com.greenidea.templete.base;

import android.app.Application;

import java.io.File;

/**
 * Created by Green on 2015/1/3.
 */
public class BaseApplication extends Application
{
    private String cachePath = null;
    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    /**
     * 获取应用的缓存目录
     * @return
     */
    public String getCachePath()
    {
        if(null == cachePath)
        {
            File temp = getExternalFilesDir(null);
            if (temp != null)
            {
                File cache = new File(temp, "cache");
                if (!cache.exists())
                {
                    if (cache.mkdirs())
                    {
                        cachePath = cache.getAbsolutePath();
                    }
                }
            }
        }
        return cachePath;
    }
}
