package com.robin.lazy.util.extend;

import java.net.URL;

/**
 * url转换工具
 * 
 * @author 江钰锋
 * @version [版本号, 2014年7月3日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UrlParser
{
    /**
     * url转换工具,确保url为绝对路径
     * 
     * @param baseUrl url的根域名
     * @param url 需要转换的url(绝对路径，或相对路径)
     * @return 返回绝对路径url
     */
    public static String urlParse(String baseUrl, String url)
    {
        String returnUrl = "";
        try
        {
            URL absoluteUrl = new URL(baseUrl);
            URL parseUrl = new URL(absoluteUrl, url);
            returnUrl = parseUrl.toString();
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        return returnUrl;
        
    }
    
}
