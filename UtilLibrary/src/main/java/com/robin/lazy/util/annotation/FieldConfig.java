package com.robin.lazy.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段配置
 * 
 * @author 江钰锋
 * @version [版本号, 2014年7月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldConfig
{
    /**
     * 设置字段名
     * 
     * @return
     */
    public String name() default "";
    
    /**
     * 字段默认值
     * 
     * @return
     */
    public String defaultValue() default "";
}
