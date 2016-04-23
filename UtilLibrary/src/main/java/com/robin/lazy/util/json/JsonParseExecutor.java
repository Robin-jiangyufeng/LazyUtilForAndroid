package com.robin.lazy.util.json;

import com.robin.lazy.util.json.annotate.JSONAnnotateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 江钰锋
 * @version [版本号, 2014年6月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class JsonParseExecutor
{
    /**
     * json原数据
     */
    private String jsonStr;
    
    /**
     * json对象
     */
    private JSONObject json;
    
    public JsonParseExecutor(String jsonStr)
    {
        this.jsonStr = jsonStr;
        init();
    }
    
    /**
     * 初始化
     * 
     * @see [类、类#方法、类#成员]
     */
    private void init()
    {
        try
        {
            json = new JSONObject(jsonStr);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 返回传入的类型对象
     * 
     * @param clazz
     * @throws JSONException
     * @see [类、类#方法、类#成员]
     */
    public <T> T fromJson(Class<T> clazz)
    {
        if (isAvailable())
        {
            String entityName = JSONAnnotateUtil.getNameByJSON(clazz);
            JSONObject js = JSONObjectUtils.getJSONObject(json, entityName, null);
            if (js != null)
            {
                return JSONEntityBuild.buildJSONEntity(js, clazz);
            }
            else
            {
                JSONEntityBuild.buildJSONEntity(json, clazz);
            }
        }
        return null;
    }
    
    /**
     * 返回传入的类型对象列表
     * 
     * @param clazz
     * @throws JSONException
     * @see [类、类#方法、类#成员]
     */
    public <T> T fromJsonObj(Class<T> clazz)
        throws JSONException
    {
        if (isAvailable())
        {
            String entityName = JSONAnnotateUtil.getNameByJSON(clazz);
            JSONObject js = JSONObjectUtils.getJSONObject(json, entityName, null);
            if (js != null)
            {
                return JSONEntityBuild.buildJSONBasicEntity(js, clazz);
            }
            else
            {
                return JSONEntityBuild.buildJSONBasicEntity(json, clazz);
            }
        }
        return null;
    }
    
    /**
     * 返回传入的类型对象列表
     * 
     * @param clazz
     * @throws JSONException
     * @see [类、类#方法、类#成员]
     */
    public <T> List<T> fromJsons(Class<T> clazz)
        throws JSONException
    {
        if (isAvailable())
        {
            JSONArray arrar = json.getJSONArray(JSONAnnotateUtil.getNameByJSON(clazz));
            return JSONEntityBuild.buildJSONArrayEntity(arrar, clazz);
        }
        return null;
    }
    
    /**
     * 判断json数据类型是否可用
     * 
     * @see [类、类#方法、类#成员]
     */
    public boolean isAvailable()
    {
        if (json != null)
        {
            return true;
        }
        return false;
    }
}
