package com.robin.lazy.util.json;

import com.robin.lazy.util.StringUtils;
import com.robin.lazy.util.json.annotate.JSONAnnotateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * json对象实体类
 * 
 * @author 江钰锋
 * @version [版本号, 2014年6月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class JSONEntityBuild
{
    /**
     * 构造对象实体
     * 
     * @param jsonObject
     * @param clazz
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static <T> T buildJSONEntity(JSONObject jsonObject, Class<T> clazz)
    {
        Field[] fields = clazz.getDeclaredFields();
        T entityT = null;
        try
        {
            entityT = clazz.newInstance();
            for (Field field : fields)
            {
                field.setAccessible(true);
                if (!JSONAnnotateUtil.isHasIgnoreField(field))
                {
                    if (JSONObjectUtils.isBaseDateType(field))
                    {
                        String name = JSONAnnotateUtil.getNameByField(field);
                        String defaultValues = JSONAnnotateUtil.getValueByField(field);
                        field.setAccessible(true);
                        setValue(field, entityT, jsonObject, name, defaultValues);
                    }
                }
                
            }
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        return entityT;
    }
    
    /**
     * 通过JSON获取一个实体对象(其中jsonObject数据都是基本数据类型)
     * 
     * @param clazz 实体类型
     * @param jsonObject json对象
     * @return 相应实体
     */
    public static <T> T buildJSONBasicEntity(JSONObject jsonObject, Class<T> clazz)
    {
        Field[] fields = clazz.getDeclaredFields();
        T basicEntityT = null;
        try
        {
            basicEntityT = clazz.newInstance();
            for (Field field : fields)
            {
                field.setAccessible(true);
                if (!JSONAnnotateUtil.isHasIgnoreField(field))
                {
                    if (JSONObjectUtils.isBaseDateType(field))
                    {
                        String name = JSONAnnotateUtil.getNameByField(field);
                        String defaultValues = JSONAnnotateUtil.getValueByField(field);
                        field.setAccessible(true);
                        setBasicValue(field, basicEntityT, jsonObject, name, defaultValues);
                    }
                }
                
            }
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        return basicEntityT;
    }
    
    /**
     * 构建json实体对象列表(对象的数据类型全部为基本数据类型)
     * 
     * @param <T>
     * 
     * @param jsonArray jsonObject列表
     * @param clazz 实体类型
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static <T extends Object> List<T> buildJSONArrayEntity(JSONArray jsonArray, Class<T> clazz)
    {
        List<T> list = new ArrayList<T>();
        try
        {
            for (int i = 0; i < jsonArray.length(); i++)
            {
                T entityT = null;
                JSONObject json = jsonArray.getJSONObject(i);
                entityT = buildJSONBasicEntity(json, clazz);
                list.add(entityT);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * 设置值到字段(数据为基本类型)
     * 
     * @param field 需要设置的字段
     * @param jsEntity 字段所属对象
     * @param jsonOb json对象
     * @param key 字段名
     */
    private static <T> void setValue(Field field, T jsEntity, JSONObject jsonOb, String key, String defaultValues)
    {
        if (JSONObjectUtils.isBaseDateType(field))
        {
            setBasicValue(field, jsEntity, jsonOb, key, defaultValues);
        }
        else if (!List.class.isAssignableFrom(field.getType()) && Object.class.isAssignableFrom(field.getType()))
        {
            setObjectValue(field, jsEntity, jsonOb, key, defaultValues);
        }
        else if (List.class.isAssignableFrom(field.getType()))
        {
            setListValue(field, jsEntity, jsonOb, key, defaultValues);
        }
    }
    
    /**
     * 设置值到字段(数据为基本类型)
     * 
     * @param field 需要设置的字段
     * @param jsEntity 字段所属对象
     * @param jsonOb json对象
     * @param key 字段名
     */
    private static <T> void setBasicValue(Field field, T jsEntity, JSONObject jsonOb, String key, String defaultValues)
    {
        try
        {
            Class<?> clazz = field.getType();
            if (String.class.isAssignableFrom(clazz))
            {
                field.set(jsEntity, JSONObjectUtils.getString(jsonOb, key, defaultValues));
            }
            else if (Integer.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz))
            {
                field.set(jsEntity, JSONObjectUtils.getInt(jsonOb, key, Integer.parseInt(defaultValues, 0)));
            }
            else if (Double.class.isAssignableFrom(clazz) || double.class.isAssignableFrom(clazz))
            {
                field.set(jsEntity, JSONObjectUtils.getDouble(jsonOb, key, Double.parseDouble(defaultValues)));
            }
            else if (Long.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz))
            {
                field.set(jsEntity, JSONObjectUtils.getLong(jsonOb, key, Long.parseLong(defaultValues, 0)));
            }
            else if (Boolean.class.isAssignableFrom(clazz))
            {
                field.set(jsEntity, JSONObjectUtils.getBoolean(jsonOb, key, Boolean.parseBoolean(defaultValues)));
            }
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 设置值到字段(数据为object对象)
     * 
     * @param <T>
     * 
     * @param field 需要设置的字段
     * @param jsEntity 字段所属对象
     * @param jsonOb json对象
     * @param key 字段名
     */
    private static <T> void setObjectValue(Field field, T jsEntity, JSONObject jsonOb, String key, String defaultValues)
    {
        try
        {
            JSONObject dv = null;
            try
            {
                if (StringUtils.isNotNull(defaultValues))
                    dv = new JSONObject(defaultValues);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            JSONObject jsOb = JSONObjectUtils.getJSONObject(jsonOb, key, dv);
            field.set(jsEntity, buildJSONBasicEntity(jsOb, field.getType()));
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 设置值到字段(数据为列表List类型)
     * 
     * @param <T>
     * 
     * @param field 需要设置的字段
     * @param jsEntity 字段所属对象
     * @param jsonOb json对象
     * @param key 字段名
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws JSONException
     */
    private static <T> void setListValue(Field field, T jsEntity, JSONObject jsonOb, String key, String defaultValues)
    {
        try
        {
            JSONArray dv = null;
            try
            {
                if (StringUtils.isNotNull(defaultValues))
                    dv = new JSONArray(defaultValues);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            JSONArray jsArray = JSONObjectUtils.getJSONArray(jsonOb, key, dv);
            Type fc = field.getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型
            if (fc != null && fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型
            {
                ParameterizedType pt = (ParameterizedType)fc;
                Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0]; // 【4】
                field.set(jsEntity, buildJSONArrayEntity(jsArray, genericClazz));
            }
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
}
