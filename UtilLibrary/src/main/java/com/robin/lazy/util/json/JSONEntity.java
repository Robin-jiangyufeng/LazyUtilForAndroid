package com.robin.lazy.util.json;

import org.json.JSONObject;

/**
 * 
 * 
 * @author 江钰锋
 * @version [版本号, 2014年6月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class JSONEntity<T extends Object>
{
    /**
     * json实体类
     */
    private T jsEntity;
    
    /**
     * json实体对象类型
     */
    private Class<T> clazz;
    
    /**
     * 
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public JSONEntity(Class<T> clazz)
        throws InstantiationException, IllegalAccessException
    {
        this.clazz = clazz;
        jsEntity = clazz.newInstance();
    }
    
    /**
     * 返回json对象的一个实例
     * 
     * @param jsonObject
     * @return
     * @see [类、类#方法、类#成员]
     */
    public T get(JSONObject jsonObject)
    {
        return createEntity(jsonObject, clazz);
    }
    
    /**
     * 通过JSON获取一个实体对象
     * 
     * @param clazz 实体类型
     * @param jsonObject json对象
     * @return 相应实体
     */
    private T createEntity(JSONObject jsonObject, Class<T> clazz)
    {
        jsEntity = JSONEntityBuild.buildJSONEntity(jsonObject, clazz);
        return jsEntity;
    }
    
    public T getJsEntity()
    {
        return jsEntity;
    }
    
    public void setJsEntity(T jsEntity)
    {
        this.jsEntity = jsEntity;
    }
    
    public Class<T> getClazz()
    {
        return clazz;
    }
    
    public void setClazz(Class<T> clazz)
    {
        this.clazz = clazz;
    }
    
    /**
     * 清理
     * 
     * @see [类、类#方法、类#成员]
     */
    public void clean()
    {
        if (jsEntity != null)
        {
            jsEntity = null;
        }
        
    }
}
