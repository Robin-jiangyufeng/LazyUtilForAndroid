/*
 * 文 件 名:  FieldExplain.java
 * 版    权:  jiang yu feng 
 * 描    述:  <描述>
 * 修 改 人:  江钰锋
 * 修改时间:  2014-3-24
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.robin.lazy.util.json.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设置json实体名,既是json数据标题
 * 
 * { "employees": [ { "firstName":"John" , "lastName":"Doe" }, { "firstName":"Anna" , "lastName":"Smith" }, {
 * "firstName":"Peter" , "lastName":"Jones" } ] }
 * 
 * 如上列子,这个指的是employees
 * 
 * @author 江钰锋
 * @version [版本号, 2014-3-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JSONEntityName
{
    /**
     * 设置json对象标题的名字
     * 
     * @return 表名
     * @see [类、类#方法、类#成员]
     */
    public String name() default "";
}
