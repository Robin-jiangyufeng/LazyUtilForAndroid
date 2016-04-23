package com.robin.lazy.util.json.annotate;

import com.robin.lazy.util.AnnotateUtils;

import java.lang.reflect.Field;

/**
 * json注解工具
 * 
 * @author 江钰锋
 * @version [版本号, 2014年6月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class JSONAnnotateUtil
{
    
    /**
     * 得到json对象名字注解,返回null的话就说明没有
     * 
     * @param <T>
     * 
     * @param field 字段
     * @return json字段配置注解
     * @see [类、类#方法、类#成员]
     */
    public static <T> JSONEntityName getJSONEntityName(Class<T> field)
    {
        return AnnotateUtils.getAnnotate(field, JSONEntityName.class);
    }
    
    /**
     * 得到feild字段是的json字段配置注解,返回null的话就说明没有
     * 
     * @param field 字段
     * @return json字段配置注解
     * @see [类、类#方法、类#成员]
     */
    public static JSONFieldConfig getFieldConfig(Field field)
    {
        return AnnotateUtils.getAnnotate(field, JSONFieldConfig.class);
    }
    
    /**
     * 判断是否有被忽略的字段配置注解
     * 
     * @param field
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isHasIgnoreField(Field field)
    {
        JSONIgnoreField annotate = AnnotateUtils.getAnnotate(field, JSONIgnoreField.class);
        if (annotate != null)
        {
            return true;
        }
        return false;
    }
    
    /**
     * 获取数据对象实例中的一条数据字段名称
     * 
     * @param field
     * @return
     */
    public static String getNameByField(Field field)
    {
        JSONFieldConfig config = getFieldConfig(field);
        if (config != null && config.name().trim().length() != 0)
        {
            return config.name();
        }
        return field.getName();
    }
    
    /**
     * 获取数据对象实例中的一条数据字段默认值
     * 
     * @param field
     * @return
     */
    public static String getValueByField(Field field)
    {
        JSONFieldConfig config = getFieldConfig(field);
        if (config != null && config.defaultValue().trim().length() != 0)
        {
            return config.defaultValue();
        }
        return null;
    }
    
    /**
     * 获取json对象标题名字
     * 
     * @param clazz
     * @return
     */
    public static String getNameByJSON(Class<?> clazz)
    {
        JSONEntityName nameAnnotate = getJSONEntityName(clazz);
        if (nameAnnotate != null && nameAnnotate.name().trim().length() != 0)
        {
            return nameAnnotate.name();
        }
        return clazz.getName();
    }
}
