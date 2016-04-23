package com.robin.lazy.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 对注解的操作工具
 * 
 * @author 江钰锋
 * @version [版本号, 2014年6月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AnnotateUtils
{
    /**
     * 判断是否有添加该注解,有就返回该注解，没有就返回空
     * 
     * @param e 传入的参
     * @param annotationType 注解类型
     * @return 注解
     * @see [类、类#方法、类#成员]
     */
    public static <E, T extends Annotation> T getAnnotate(E e, Class<T> annotationType)
    {
        T at = null;
        if (e instanceof Method)
        {
            at = ((Method)e).getAnnotation(annotationType);
        }
        else if (e instanceof Field)
        {
            at = ((Field)e).getAnnotation(annotationType);
        }
        else if (e instanceof Class)
        {
            at = (T)((Class)e).getAnnotation(annotationType);
        }
        return at;
    }
}
