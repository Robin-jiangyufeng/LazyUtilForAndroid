package com.robin.lazy.util.extend.share;

import android.content.Context;
import android.content.Intent;

/**
 * 
 * 邮件分享的类
 * 
 * @author 江钰锋
 * @version [版本号, 2014年7月3日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MailShareUtil
{
    /**
     * 邮件分享
     * 
     * @param mContext
     * @param title 邮件的标题
     * @param text 邮件的内容
     * @return
     */
    public static Boolean sendMail(Context mContext, String title, String text)
    {
        // 调用系统发邮件
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // 设置文本格式
        emailIntent.setType("text/plain");
        // 设置对方邮件地址
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "");
        // 设置标题内容
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        // 设置邮件文本内容
        emailIntent.putExtra(Intent.EXTRA_TEXT, text);
        mContext.startActivity(Intent.createChooser(emailIntent, "Choose Email Client"));
        return null;
    }
}
