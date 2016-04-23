package com.robin.lazy.util.json.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * json格式字段配置
 * 
 * @author 江钰锋
 * @version [版本号, 2014年6月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JSONFieldConfig
{
    /**
     * 设置json字段名
     * 
     * @return
     */
    public String name() default "";
    
    /**
     * json字段默认值
     * 
     * @return
     */
    public String defaultValue() default "";
}
