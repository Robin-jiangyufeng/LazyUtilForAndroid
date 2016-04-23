package com.robin.lazy.util.json;

import org.json.JSONObject;

/**
 * json实体基类
 * 
 * @author 江钰锋
 * @version [版本号, 2014年6月26日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface JSONEntityBase<T extends Object>
{
    /**
     * 实体类型(有基本数据类型,有对象,已经列表)
     * 
     * @author 江钰锋
     * @version [版本号, 2014年6月26日]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */
    public enum EntityType
    {
        /**
         * 基本数据类型
         */
        TYPE_BASIC,
        
        /**
         * 一般类型
         */
        TYPE_OBJECT,
        
        /**
         * 列表类型
         */
        TYPE_LIST
    }
    
    /**
     * 得到json数据转对象后的对象实体
     * 
     * @param json
     * @return
     * @see [类、类#方法、类#成员]
     */
    T getEntity(JSONObject json);
}
