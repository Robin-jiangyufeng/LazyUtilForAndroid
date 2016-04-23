package com.robin.lazy.util.extend;

import java.text.DecimalFormat;

/**
 * 文件大小单位转换工具
 * 
 * @author 江钰锋
 * @version [版本号, 2014年7月3日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileSizeFormat
{
    private static String KB_UNIT_NAME = "KB";
    
    private static String B_UNIT_NAME = "B";
    
    private static String MB_UNIT_NAME = "MB";
    
    private static String G_UNIT_NAME = "G";
    
    public static String getSizeString(long size)
    {
        if (size < 1024)
        {
            return String.valueOf(size) + B_UNIT_NAME;
        }
        else
        {
            size = size / 1024;
        }
        if (size < 1024)
        {
            return String.valueOf(size) + KB_UNIT_NAME;
        }
        else
        {
            size = size * 100 / 1024;
        }
        
        return String.valueOf((size / 100)) + "." + ((size % 100) < 10 ? "0" : "") + String.valueOf((size % 100))
            + MB_UNIT_NAME;
    }
    
    /**
     * 以Mb为单位保留两位小数
     * 
     * @param dirSize
     * @return
     */
    public static String getMbSize(long dirSize)
    {
        double size = 0;
        size = (dirSize + 0.0) / (1024 * 1024);
        DecimalFormat df = new DecimalFormat("0.00");// 以Mb为单位保留两位小数
        String filesize = df.format(size);
        return filesize;
    }
    
    /**
     * 以kb为单位保留两位小数
     * 
     * @param dirSize
     * @return
     */
    public static String getKBSize(long dirSize)
    {
        double size = 0;
        size = (dirSize + 0.0) / 1024;
        DecimalFormat df = new DecimalFormat("0.00");// 以KB为单位保留两位小数
        String filesize = df.format(size);
        return filesize;
    }
    
    /**
     * 返回文件大小
     * 
     * @param dirSize
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String FormetFileSize(long dirSize)
    {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#0.00");
        String fileSizeString = "";
        if (dirSize < 1024)
        {
            fileSizeString = df.format((double)dirSize) + B_UNIT_NAME;
        }
        else if (dirSize < 1048576)
        {
            fileSizeString = df.format((double)dirSize / 1024) + KB_UNIT_NAME;
        }
        else if (dirSize < 1073741824)
        {
            fileSizeString = df.format((double)dirSize / 1048576) + MB_UNIT_NAME;
        }
        else
        {
            fileSizeString = df.format((double)dirSize / 1073741824) + G_UNIT_NAME;
        }
        return fileSizeString;
    }
}
