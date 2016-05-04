/*
 * Copyright (C) 2013  WhiteCat 白猫 (www.thinkandroid.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robin.lazy.util;

import com.robin.lazy.util.annotation.FieldConfig;
import com.robin.lazy.util.annotation.IgnoreField;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * 反射的一些工具
 * 
 * @author 江钰锋
 * @version [版本号, 2014年7月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ReflectUtils
{
    /**
     * 检测实体属性是否已经被标注为 不被识别
     * 
     * @param field 字段
     * @return
     */
    public static boolean isTransient(Field field)
    {
        return field.getAnnotation(IgnoreField.class) != null;
    }
    
    /**
     * 是否为基本的数据类型
     * 
     * @param field
     * @return
     */
    public static boolean isBaseDateType(Field field)
    {
        Class<?> clazz = field.getType();
        return clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(Byte.class)
            || clazz.equals(Long.class) || clazz.equals(Double.class) || clazz.equals(Float.class)
            || clazz.equals(Character.class) || clazz.equals(Short.class) || clazz.equals(Boolean.class)
            || clazz.equals(Date.class) || clazz.equals(Date.class) || clazz.equals(java.sql.Date.class)
            || clazz.isPrimitive();
    }
    
    /**
     * 获得配置名
     * 
     * @param field
     * @return
     */
    public static String getFieldName(Field field)
    {
        FieldConfig column = field.getAnnotation(FieldConfig.class);
        if (column != null && column.name().trim().length() != 0)
        {
            return column.name();
        }
        return field.getName();
    }
    
    /** 
     * 根据索引获得超类的参数类型 
     * @param clazz 超类类型 
     * @param index 索引 
     */  
    public static Class<?> getSuperClassGenricType(Class<?> clazz,int index) { 
    	//返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
    	Type genType = clazz.getGenericSuperclass(); 
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;   
        }  
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();                   
        if (index >= params.length || index < 0) { 
        	return Object.class;
        }      
        if (!(params[index] instanceof Class)) {
            return Object.class;   
        }   
        return (Class<?>) params[index]; 
    }

    /**
     * 根据索引获得超类的参数类型
     * @param clazz 超类类型
     * @param genricIndex 接口中范型的下标
     */
    public static Class<?> getInterfacesGenricType(Class<?> clazz,int genricIndex) {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的接口的 Type数组。
        Type[] genTypes = clazz.getGenericInterfaces();
        if (!(genTypes[0] instanceof ParameterizedType)) {
            return Object.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genTypes[0]).getActualTypeArguments();
        if (genricIndex >= params.length || genricIndex < 0) {
            return Object.class;
        }
        if (!(params[genricIndex] instanceof Class)) {
            return Object.class;
        }
        return (Class<?>) params[genricIndex];
    }

    /**
     * 根据索引获得超类的参数类型
     * @param genericType 超类类型
     * @param genricIndex 接口中范型的下标
     */
    public static Class<?> getInterfacesGenricType(Type genericType,int genricIndex) {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的接口的 Type数组。
        if (!(genericType instanceof ParameterizedType)) {
            return Object.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genericType).getActualTypeArguments();
        if (genricIndex >= params.length || genricIndex < 0) {
            return Object.class;
        }
        if (!(params[genricIndex] instanceof Class)) {
            return Object.class;
        }
        return (Class<?>) params[genricIndex];
    }
}
