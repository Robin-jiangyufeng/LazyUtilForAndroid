/*
 * Copyright (C) 2013  WhiteCat (www.thinkandroid.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robin.lazy.util;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 * 
 * @author 江钰锋
 * @version [版本号, 2014年6月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class StringUtils
{
    /**
     * 编码格式
     */
    public static String ENCODING = "UTF-8";
    
    /**
     * 电子邮件模板
     */
    private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    
    /** (0-9中文数字) */
    private final static String[] figDecode = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    
    /**
     * 单位
     */
    private final static String[] digitList = {"", "十", "百", "千", "万"};
    
    /**
     * 16进制表示的字符串转换为字节数组。
     * 
     * @param hexString
     *            16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        char[] hexChars = hexString.toCharArray();
        int length = hexString.length();
        byte[] d = new byte[length >>> 1];
        for (int n = 0; n < length; n += 2) {
            String item = new String(hexChars, n, 2);
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            d[n >>> 1] = (byte) Integer.parseInt(item, 16);
        }
        return d;
    }
    
    /**
     * @param password
     * @return 判断是否是6-16 的字母和数字
     */
    public static boolean isStrong(String password)
    {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }
    
    /**
     * 字符串截取
     * 
     * @param str
     * @param length
     * @return
     * @throws Exception
     */
    public static String subString(String str, int length)
        throws Exception
    {
        
        byte[] bytes = str.getBytes("Unicode");
        int n = 0; // 表示当前的字节数
        int i = 2; // 要截取的字节数，从第3个字节开始
        for (; i < bytes.length && n < length; i++)
        {
            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
            if (i % 2 == 1)
            {
                n++; // 在UCS2第二个字节时n加1
            }
            else
            {
                // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                if (bytes[i] != 0)
                {
                    n++;
                }
            }
        }
        // 如果i为奇数时，处理成偶数
        if (i % 2 == 1)
        {
            // 该UCS2字符是汉字时，去掉这个截一半的汉字
            if (bytes[i - 1] != 0)
                i = i - 1;
            // 该UCS2字符是字母或数字，则保留该字符
            else
                i = i + 1;
        }
        return new String(bytes, 0, i, "Unicode");
    }
    
    /**
     * 计算微博内容的长度 1个汉字 == 两个英文字母所占的长度 标点符号区分英文和中文
     * 
     * @param c 所要统计的字符序列
     * @return 返回字符序列计算的长度
     */
    public static long calculateWeiboLength(CharSequence c)
    {
        
        double len = 0;
        for (int i = 0; i < c.length(); i++)
        {
            int temp = (int)c.charAt(i);
            if (temp > 0 && temp < 127)
            {
                len += 0.5;
            }
            else
            {
                len++;
            }
        }
        return Math.round(len);
    }
    
    /**
     * 分割字符串
     * 
     * @param str String 原始字符串
     * @param splitsign String 分隔符
     * @return String[] 分割后的字符串数组
     */
    public static String[] split(String str, String splitsign)
    {
        int index;
        if (str == null || splitsign == null)
            return null;
        ArrayList<String> al = new ArrayList<String>();
        while ((index = str.indexOf(splitsign)) != -1)
        {
            al.add(str.substring(0, index));
            str = str.substring(index + splitsign.length());
        }
        al.add(str);
        return (String[])al.toArray(new String[0]);
    }
    
    /**
     * 替换字符串
     * 
     * @param from String 原始字符串
     * @param to String 目标字符串
     * @param source String 母字符串
     * @return String 替换后的字符串
     */
    public static String replace(String from, String to, String source)
    {
        if (source == null || from == null || to == null)
            return null;
        StringBuffer bf = new StringBuffer("");
        int index = -1;
        while ((index = source.indexOf(from)) != -1)
        {
            bf.append(source.substring(0, index) + to);
            source = source.substring(index + from.length());
            index = source.indexOf(from);
        }
        bf.append(source);
        return bf.toString();
    }
    
    /***
     * 替换数字为中文大写数字
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String replaceFig(int nb)
    {
        StringBuffer bf = new StringBuffer();
        for (int i = 0; i < digitList.length; i++)
        {
            bf.insert(0, figDecode[nb % 10] + digitList[i]);
            
            if (nb < 10) // 除到到只剩个位数就退出循环
                break;
            nb /= 10;
            
            if (i >= digitList.length - 1)// 假如数目大于万就重新从个位数开始数
                i = 0;
        }
        return bf.toString();
    }
    
    /**
     * 替换字符串，能能够在HTML页面上直接显示(替换双引号和小于号)
     * 
     * @param str String 原始字符串
     * @return String 替换后的字符串
     */
    public static String htmlencode(String str)
    {
        if (str == null)
        {
            return null;
        }
        
        return replace("\"", "&quot;", replace("<", "&lt;", str));
    }
    
    /**
     * 替换字符串，将被编码的转换成原始码（替换成双引号和小于号）
     * 
     * @param str String
     * @return String
     */
    public static String htmldecode(String str)
    {
        if (str == null)
        {
            return null;
        }
        
        return replace("&quot;", "\"", replace("&lt;", "<", str));
    }
    
    private static final String _BR = "<br/>";
    
    /**
     * 在页面上直接显示文本内容，替换小于号，空格，回车，TAB
     * 
     * @param str String 原始字符串
     * @return String 替换后的字符串
     */
    public static String htmlshow(String str)
    {
        if (str == null)
        {
            return null;
        }
        
        str = replace("<", "&lt;", str);
        str = replace(" ", "&nbsp;", str);
        str = replace("\r\n", _BR, str);
        str = replace("\n", _BR, str);
        str = replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;", str);
        return str;
    }
    
    /**
     * 返回指定字节长度的字符串
     * 
     * @param str String 字符串
     * @param length int 指定长度
     * @return String 返回的字符串
     */
    public static String toLength(String str, int length)
    {
        if (str == null)
        {
            return null;
        }
        if (length <= 0)
        {
            return "";
        }
        try
        {
            if (str.getBytes("GBK").length <= length)
            {
                return str;
            }
        }
        catch (Exception ex)
        {
        }
        StringBuffer buff = new StringBuffer();
        
        int index = 0;
        char c;
        length -= 3;
        while (length > 0)
        {
            c = str.charAt(index);
            if (c < 128)
            {
                length--;
            }
            else
            {
                length--;
                length--;
            }
            buff.append(c);
            index++;
        }
        buff.append("...");
        return buff.toString();
    }
    
    /**
     * 判断url是否是https安全连接
     * 
     * @param url
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isHttpsUrl(String url)
    {
        if (url != null)
        {
            return url.toLowerCase(Locale.US).trim().startsWith("https");
        }
        return false;
    }
    
    /**
     * 判断url是否是http普通连接
     * 
     * @param url
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isHttpUrl(String url)
    {
        if (url != null)
        {
            return url.toLowerCase(Locale.US).trim().startsWith("http");
        }
        return false;
    }
    
    /**
     * 获取url的后缀名
     * 
     * @param str
     * @return
     */
    public static String getUrlFileName(String urlString)
    {
        String fileName = urlString.substring(urlString.lastIndexOf("/"));
        fileName = fileName.substring(1, fileName.length());
        if (fileName.equalsIgnoreCase(""))
        {
            Calendar c = Calendar.getInstance();
            fileName =
                c.get(Calendar.YEAR) + "" + c.get(Calendar.MONTH) + "" + c.get(Calendar.DAY_OF_MONTH) + ""
                    + c.get(Calendar.MINUTE);
            
        }
        return fileName;
    }
    
    /**
     * 通过url得到文件前缀
     * 
     * @param fileName
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getPrefixByFileName(String fileName)
    {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
    
    /**
     * 通过url得到文件类型
     * 
     * @param fileName
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getFileTypeByFileName(String fileName)
    {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    
    /**
     * 解析组合http要发送的数据
     * 
     * @param apiparamsMap
     * @return
     */
    public static String getHttpRequestData(Map<String, Object> apiparamsMap)
    {
        StringBuilder param = new StringBuilder();
        for (Iterator<Map.Entry<String, Object>> it = apiparamsMap.entrySet().iterator(); it.hasNext();)
        {
            Map.Entry<String, Object> e = it.next();
            param.append("&").append(e.getKey()).append("=").append(e.getValue());
        }
        return param.toString().substring(1);
    }
    
    /**
     * 替换掉特殊字符
     * 
     * @param str 原始字符串
     * @return 替换后的字符串
     */
    public static String replaceSomeString(String str)
    {
        String dest = "";
        try
        {
            if (str != null)
            {
                str = str.replaceAll("\r", "");
                str = str.replaceAll("&gt;", ">");
                str = str.replaceAll("&ldquo;", "“");
                str = str.replaceAll("&rdquo;", "”");
                str = str.replaceAll("&#39;", "'");
                str = str.replaceAll("&nbsp;", "");
                str = str.replaceAll("<br\\s*/>", "\n");
                str = str.replaceAll("&quot;", "\"");
                str = str.replaceAll("&lt;", "<");
                str = str.replaceAll("&lsquo;", "《");
                str = str.replaceAll("&rsquo;", "》");
                str = str.replaceAll("&middot;", "·");
                str = str.replace("&mdash;", "—");
                str = str.replace("&hellip;", "…");
                str = str.replace("&amp;", "×");
                str = str.replaceAll("\\s*", "");
                str = str.trim();
                str = str.replaceAll("<p>", "\n      ");
                str = str.replaceAll("</p>", "");
                str = str.replaceAll("<div.*?>", "\n      ");
                str = str.replaceAll("</div>", "");
                dest = str;
            }
        }
        catch (Exception e)
        {
            
        }
        return dest;
    }
    
    /**
     * 清除文本里面的HTML标签
     * 
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr)
    {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        Log.v("htmlStr", htmlStr);
        try
        {
            Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            Matcher m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            
            Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            Matcher m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            
            Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            Matcher m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
        }
        catch (Exception e)
        {
            
        }
        
        return htmlStr;
    }
    
    /**
     * 去掉字符空格，换行符等
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String delSpace(String str)
    {
        if (str != null)
        {
            str = str.replaceAll("\r", "");
            str = str.replaceAll("\n", "");
            str = str.replace(" ", "");
        }
        return str;
    }
    
    /**
     * 检查字符串是否存在值，如果存在为true,
     * 
     * @param str 待检验的字符串
     * @return 当 str 不为 null 或 "" 就返回 true
     */
    public static boolean isNotNull(String str)
    {
        return (str != null && !"".equalsIgnoreCase(str.trim()));
    }
    
    public static String trimmy(String str)
    {
        String dest = "";
        if (str != null)
        {
            str = str.replaceAll("-", "");
            str = str.replaceAll("\\+", "");
            dest = str;
        }
        return dest;
    }
    
    /**
     * 去掉空白
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String replaceBlank(String str)
    {
        
        String dest = "";
        if (str != null)
        {
            Pattern p = Pattern.compile("\r");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    
    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     * 
     * @param input
     * @return boolean 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(String input)
    {
        if (input == null || "".equals(input))
            return true;
        
        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n')
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 判断是不是一个合法的电子邮件地址
     * 
     * @param email
     * @return
     */
    public static boolean isEmail(String email)
    {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }
    
    /**
     * 判断是不是合法手机 handset 手机号码
     */
    public static boolean isHandset(String handset)
    {
        try
        {
            if (!handset.substring(0, 1).equals("1"))
            {
                return false;
            }
            if (handset == null || handset.length() != 11)
            {
                return false;
            }
            String check = "^[0123456789]+$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(handset);
            boolean isMatched = matcher.matches();
            if (isMatched)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (RuntimeException e)
        {
            return false;
        }
    }
    
    /**
     * 判断输入的字符串是否为纯汉字
     * 
     * @param str 传入的字符窜
     * @return 如果是纯汉字返回true,否则返回false
     */
    public static boolean isChinese(String str)
    {
        Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
        return pattern.matcher(str).matches();
    }
    
    /**
     * 判断是否为数字
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches())
        {
            return false;
        }
        return true;
    }
    
    /**
     * 判断是否为整数
     * 
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isInteger(String str)
    {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    
    /**
     * 判断是否为浮点数，包括double和float
     * 
     * @param str 传入的字符串
     * @return 是浮点数返回true,否则返回false
     */
    public static boolean isDouble(String str)
    {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }
    
    /**
     * 判断手机号码是否正确
     * 
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNumber)
    {
        phoneNumber = trimmy(phoneNumber);
        MobileFormat mobile = new MobileFormat(phoneNumber);
        return mobile.isLawful();
    }
    
    /**
     * 判断是否为url
     * 
     * @param email
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean checkEmail(String email)
    {
        
        Pattern pattern = Pattern.compile("^\\w+([-.]\\w+)*@\\w+([-]\\w+)*\\.(\\w+([-]\\w+)*\\.)*[a-z]{2,3}$");
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches())
        {
            return true;
        }
        return false;
    }
    
    /**
     * 根据url获取文件名
     * 
     * @param url
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getFileNameFromUrl(String url)
    {
        
        // 名字不能只用这个
        // 通过 ‘？’ 和 ‘/’ 判断文件名
        String extName = "";
        String filename;
        int index = url.lastIndexOf('?');
        if (index > 1)
        {
            extName = url.substring(url.lastIndexOf('.') + 1, index);
        }
        else
        {
            extName = url.substring(url.lastIndexOf('.') + 1);
        }
        filename = hashKeyForDisk(url) + "." + extName;
        return filename;
    }
    
    /**
     * 一个散列方法,改变一个字符串(如URL)到一个散列适合使用作为一个磁盘文件名。
     * 
     * @param key
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String hashKeyForDisk(String key)
    {
        String cacheKey;
        try
        {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }
    
    /**
     * byte[]数组转换为16进制的字符串。
     * 
     * @param data
     *            要转换的字节数组。
     * @return 转换后的结果。
     */
    public static final String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++)
        {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1)
            {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    
    /**
     * unicode 转换成 中文
     * 
     * @author fanhui 2007-3-15
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString)
    {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;)
        {
            aChar = theString.charAt(x++);
            if (aChar == '\\')
            {
                aChar = theString.charAt(x++);
                if (aChar == 'u')
                {
                    int value = 0;
                    for (int i = 0; i < 4; i++)
                    {
                        aChar = theString.charAt(x++);
                        switch (aChar)
                        {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed      encoding.");
                        }
                    }
                    outBuffer.append((char)value);
                }
                else
                {
                    if (aChar == 't')
                    {
                        aChar = '\t';
                    }
                    else if (aChar == 'r')
                    {
                        aChar = '\r';
                    }
                    else if (aChar == 'n')
                    {
                        aChar = '\n';
                    }
                    else if (aChar == 'f')
                    {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            }
            else
            {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }
    
    /**
     * string转uncode
     * 
     * @param gbString
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String gbEncoding(String gbString)
    {
        char[] utfBytes = gbString.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++)
        {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);
            if (hexB.length() <= 2)
            {
                hexB = "00" + hexB;
            }
            buffer.append("" + hexB);
        }
        return buffer.substring(0);
    }
}
