package com.robin.lazy.util.json;

import com.robin.lazy.util.json.annotate.JSONAnnotateUtil;
import com.robin.lazy.util.json.annotate.JSONFieldConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * json构造工具类
 * 
 * @author 江钰锋
 * @version [版本号, 2014年6月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class JSONFieldInfo
{
    /**
     * json字段名称
     */
    private String name;
    
    /**
     * json字段默认值
     */
    private Object values;
    
    /**
     * 字段类型
     */
    private Class<?> type;
    
    /**
     * 是否被忽略
     */
    private boolean isIgnore;
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @param json
     * @throws JSONException
     * @see [类、类#方法、类#成员]
     */
    public static void valueOf(JSONObject json, Field field)
        throws JSONException
    {
        if (!JSONAnnotateUtil.isHasIgnoreField(field))
        {
            JSONFieldInfo fieldInfo = new JSONFieldInfo();
            fieldInfo.setIgnore(false);
            JSONFieldConfig annotate = JSONAnnotateUtil.getFieldConfig(field);
            if (annotate != null)
            {
                fieldInfo.setName(annotate.name());
                fieldInfo.setValues(json.get(fieldInfo.getName()));
            }
        }
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Object getValues()
    {
        return values;
    }
    
    public void setValues(Object values)
    {
        this.values = values;
    }
    
    public Class<?> getType()
    {
        return type;
    }
    
    public void setType(Class<?> type)
    {
        this.type = type;
    }
    
    public boolean isIgnore()
    {
        return isIgnore;
    }
    
    public void setIgnore(boolean isIgnore)
    {
        this.isIgnore = isIgnore;
    }
    
}
