/*
 * 文 件 名:  AESHelper.java
 * 版    权:  Haier, Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 0152
 * 修改时间:  2015年1月14日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.robin.lazy.util.security;

import android.annotation.SuppressLint;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES对称加密解密类
 * 
 * @author 江钰锋 0152
 * @version [版本号, 2015年1月14日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@SuppressLint("DefaultLocale")
public class AESHelper
{
    /** 算法/模式/填充 **/
    private static final String CipherMode = "AES/ECB/PKCS5Padding";
    
    /**
     * 创建密钥
     * 
     * @param password
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static SecretKeySpec createKey(String password)
    {
        byte[] data = null;
        if (password == null)
        {
            password = "";
        }
        StringBuffer sb = new StringBuffer(32);
        sb.append(password);
        while (sb.length() < 32)
        {
            sb.append("0");
        }
        if (sb.length() > 32)
        {
            sb.setLength(32);
        }
        try
        {
            data = sb.toString().getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return new SecretKeySpec(data, "AES");
    }
    
    /**
     * 加密字节数据
     * 
     * @param content
     * @param password
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static byte[] encrypt(byte[] content, String password)
    {
        try
        {
            SecretKeySpec key = createKey(password);
            System.out.println(key);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 加密(结果为16进制字符串)
     * 
     * @param content
     * @param password
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String encrypt(String content, String password)
    {
        byte[] data = null;
        try
        {
            data = content.getBytes("UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        data = encrypt(data, password);
        String result = byte2hex(data);
        return result;
    }
    
    /**
     * 解密字节数组
     * 
     * @param content
     * @param password
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static byte[] decrypt(byte[] content, String password)
    {
        try
        {
            SecretKeySpec key = createKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 解密16进制的字符串为字符串
     * 
     * @param content
     * @param password
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String decrypt(String content, String password)
    {
        byte[] data = null;
        try
        {
            data = hex2byte(content);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        data = decrypt(data, password);
        if (data == null)
            return null;
        String result = null;
        try
        {
            result = new String(data, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 字节数组转成16进制字符串
     * 
     * @param b
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String byte2hex(byte[] b)
    {
        StringBuffer sb = new StringBuffer(b.length * 2);
        String tmp = "";
        for (int n = 0; n < b.length; n++)
        {
            tmp = (Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1)
            {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase();
    }
    
    /**
     * 将hex字符串转换成字节数组
     * 
     * @param inputString
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static byte[] hex2byte(String inputString)
    {
        if (inputString == null || inputString.length() < 2)
        {
            return new byte[0];
        }
        inputString = inputString.toUpperCase();
        int l = inputString.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i)
        {
            String tmp = inputString.substring(2 * i, 2 * i + 2);
            result[i] = (byte)(Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }
}
