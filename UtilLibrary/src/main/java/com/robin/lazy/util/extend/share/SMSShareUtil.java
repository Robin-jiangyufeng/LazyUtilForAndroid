package com.robin.lazy.util.extend.share;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 
 * 短信分享的类
 * 
 * @author 江钰锋
 * @version [版本号, 2014年7月3日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SMSShareUtil
{
    /**
     * 短信分享
     * 
     * @param mContext
     * @param smstext 短信分享内容
     * @return
     */
    public static Boolean sendSms(Context mContext, String smstext)
    {
        Uri smsToUri = Uri.parse("smsto:");
        Intent mIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        mIntent.putExtra("sms_body", smstext);
        mContext.startActivity(mIntent);
        return null;
    }
    
}