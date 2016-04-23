package com.robin.lazy.util.exception;

import android.os.Build;
import android.os.Looper;

import com.robin.lazy.logger.LazyLogger;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于处理接收应用程序的崩溃信息
 * @author Administrator
 *
 */
public class CrashHandler implements UncaughtExceptionHandler{

	/**
	 * 系统默认的UncaughtException处理类   
	 */
    private UncaughtExceptionHandler mDefaultHandler;
    /**
     * 用来存储设备信息和异常信息  
     */
    private Map<String, String> infos = new HashMap<String, String>();  
    /**
     * 用来处理异常发生时的动作
     */
    private CrashHandleListener mCrashHandleListener;
  	
    /**
     * 保证只有一个CrashHandler实例
	 * 缓存Log日志的任务执行器
     */
    public CrashHandler() {
    	
    }  
    /**
     * 初始化 
     * @param listener 用来处理异常发生时的动作(可以为空)
     */
    public void init(CrashHandleListener listener) {  
     
        //获取系统默认的UncaughtException处理器  
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();  
        //设置该CrashHandler为程序的默认处理器  
        Thread.setDefaultUncaughtExceptionHandler(this); 
		mCrashHandleListener=listener;
    }  
    
  
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				LazyLogger.t("CrashHandler").e("error : ", e);
			}
			// 退出程序
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}
	 /** 
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 
     *  
     * @param ex 
     * @return true:如果处理了该异常信息返回true;否则返回false. 
     */  
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		// 使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				if (mCrashHandleListener != null) {
					mCrashHandleListener.crashHandle();
				}
				Looper.loop();
			}
		}.start();
		// 收集设备参数信息
		collectDeviceInfo();
		LazyLogger.t("CrashHandler").e(ex, "crash_massage");
		return true;
	}  
    
    /** 
     * 收集设备参数信息 
     * @param
     */  
    public void collectDeviceInfo() {  
        
        Field[] fields = Build.class.getDeclaredFields();  
        for (Field field : fields) {  
            try {  
                field.setAccessible(true);  
                infos.put(field.getName(), field.get(null).toString());  
                LazyLogger.t("CrashHandler").e(field.getName() + " : " + field.get(null));  
            } catch (Exception e) {  
            	LazyLogger.t("CrashHandler").e("an error occured when collect crash info", e);  
            }  
        }  
    } 
}
