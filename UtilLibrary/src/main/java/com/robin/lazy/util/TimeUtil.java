package com.robin.lazy.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author way
 * 
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil
{
    
    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>()
        {
            @Override
            protected SimpleDateFormat initialValue()
            {
                return new SimpleDateFormat("yyyy-MM-dd");
            }
        };
        
        private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>()
        {
            @Override
            protected SimpleDateFormat initialValue()
            {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
        };
    
    public static String getTime(long time)
    {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
        return format.format(new Date(time));
    }
    
    public static String getHourAndMin(long time)
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }
    
    public static String getChatTime(long timesamp)
    {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(timesamp);
        int temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));
        
        switch (temp)
        {
            case 0:
                result = "今天 " + getHourAndMin(timesamp);
                break;
            case 1:
                result = "昨天 " + getHourAndMin(timesamp);
                break;
            case 2:
                result = "前天 " + getHourAndMin(timesamp);
                break;
            
            default:
                // result = temp + "天前 ";
                result = getTime(timesamp);
                break;
        }
        
        return result;
    }
    
    /**
     * 以友好的方式显示时间
     * 
     * @param sdate
     * @return
     */
    public static String friendlyTime(String sdate)
    {
        Date time = toDate(sdate);
        if (time == null)
        {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();
        
        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate))
        {
            int hour = (int)((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }
        
        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int)(ct - lt);
        if (days == 0)
        {
            int hour = (int)((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
        }
        else if (days == 1)
        {
            ftime = "昨天";
        }
        else if (days == 2)
        {
            ftime = "前天";
        }
        else if (days > 2 && days <= 10)
        {
            ftime = days + "天前";
        }
        else if (days > 10)
        {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }
    
    /**
     * 判断给定字符串时间是否为今日
     * 
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate)
    {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null)
        {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate))
            {
                b = true;
            }
        }
        return b;
    }
    
    /**
     * 将字符串转位日期类型
     * 
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate)
    {
        try
        {
            return dateFormater.get().parse(sdate);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    
}
