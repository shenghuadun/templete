package com.greenidea.templete.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Green on 2015/1/2.
 */
public class HttpUtil
{
    private static String doRequeast(String url, Map<String, String> params, String method)
    {
        // 构建请求参数
        StringBuffer sb = new StringBuffer();
        if (params != null)
        {
            for (Map.Entry<String, String> e : params.entrySet())
            {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
            sb.substring(0, sb.length() - 1);
        }

        HttpURLConnection con = null;
        OutputStream os = null;
        OutputStreamWriter osw = null;

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        URL u = null;
        StringBuffer buffer = new StringBuffer();
        try
        {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod(method);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            os = con.getOutputStream();
            osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(sb.toString());
            osw.flush();

            if (con.getResponseCode() >= 300)
            {
                Log.e("HttpUtil", "http请求失败");
                return null;
            }

            // 读取返回内容
            is = con.getInputStream();
            isr = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(isr);
            String temp;
            while ((temp = br.readLine()) != null)
            {
                buffer.append(temp);
                buffer.append("\n");
            }
        }
        catch (MalformedURLException e)
        {
            Log.e("HttpUtil", "URL不合法");
            return null;
        }
        catch (ProtocolException e)
        {
            Log.e("HttpUtil", "不支持的协议");
            e.printStackTrace();
            return null;
        }
        catch (UnsupportedEncodingException e)
        {
            Log.e("HttpUtil", "不支持的编码");
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            Log.e("HttpUtil", "IO错误");
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                if(osw != null)
                {
                    osw.close();
                }
                if(os != null)
                {
                    os.close();
                }
                if(br != null)
                {
                    br.close();
                }
                if(isr != null)
                {
                    isr.close();
                }
                if(is != null)
                {
                    is.close();
                }
                if(con != null)
                {
                    con.disconnect();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return buffer.toString();
    }

    public static String doGet(String url, Map<String, String> param)
    {
        return doRequeast(url, param, "get");
    }

    public static String doPost(String url, Map<String, String> param)
    {
        return doRequeast(url, param, "post");
    }

    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null)
        {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null)
            {
                if (networkInfo.isConnected())
                {
                    return true;
                }
            }
        }
        return false;
    }
}
