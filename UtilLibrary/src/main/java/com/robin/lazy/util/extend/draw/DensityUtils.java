package com.robin.lazy.util.extend.draw;

import android.content.Context;

/**
 * 是一个像素与dp转换的工具
 * 
 * @author 江钰锋
 * @version [版本号, 2014年7月3日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DensityUtils
{
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * 
     * @param context
     * @param dpValue dp值
     * @return 返回像素值
     */
    public static int dipTopx(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
    
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * 
     * @param context
     * @param pxValue 像素值
     * @return 返回dp值
     */
    public static int pxTodip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
    
}
