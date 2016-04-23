package com.robin.lazy.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记为被忽略的字段
 * 
 * @author 江钰锋
 * @version [版本号, 2014年7月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IgnoreField
{
    
}
