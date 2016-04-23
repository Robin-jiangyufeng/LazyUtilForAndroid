package com.robin.lazy.util.json;

import com.robin.lazy.logger.LazyLogger;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

import java.io.IOException;
import java.io.StringWriter;

public class JSONUtil
{
    private static ObjectMapper objectMapper;
    
    /**
     * 懒惰单例模式得到ObjectMapper实例 此对象为Jackson的核心
     */
    private static ObjectMapper getMapper()
    {
        if (objectMapper == null)
        {
            synchronized (JSONUtil.class)
            {
                if (objectMapper == null)
                {
                    objectMapper = new ObjectMapper();
                    // 当找不到对应的序列化器时 忽略此字段
                    objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
                    // 使Jackson JSON支持Unicode编码非ASCII字符
                    CustomSerializerFactory serializerFactory = new CustomSerializerFactory();
                    serializerFactory.addSpecificMapping(String.class, new StringUnicodeSerializer());
                    objectMapper.setSerializerFactory(serializerFactory);
                }
            }
            // 支持结束
        }
        return objectMapper;
    }
    
    /**
     * 创建JSON生成器的静态方法, 使用标准输出
     * 
     * @return
     */
    private static JsonGenerator getGenerator(StringWriter sw)
    {
        try
        {
            return getMapper().getJsonFactory().createJsonGenerator(sw);
        }
        catch (IOException e)
        {
            return null;
        }
    }
    
    /**
     * JSON对象序列化
     */
    public static String toJSON(Object obj)
    {
        StringWriter sw = new StringWriter();
        JsonGenerator jsonGen = getGenerator(sw);
        if (jsonGen == null)
        {
            try
            {
                sw.close();
            }
            catch (IOException e)
            {
            }
            return null;
        }
        try
        {
            // 由于在getGenerator方法中指定了OutputStream为sw
            // 因此调用writeObject会将数据输出到sw
            jsonGen.writeObject(obj);
            // 由于采用流式输出 在输出完毕后务必清空缓冲区并关闭输出流
            jsonGen.flush();
            jsonGen.close();
            return sw.toString();
        }
        catch (JsonGenerationException jge)
        {
            LazyLogger.e(jge, "JSON生成错误" + jge.getMessage());
        }
        catch (IOException ioe)
        {
            LazyLogger.e(ioe, "JSON输入输出错误" + ioe.getMessage());
        }
        return null;
    }
    
    /**
     * JSON对象反序列化
     */
    public static <T> T fromJSON(String json, Class<T> clazz)
    {
        try
        {
            
            return getMapper().readValue(json, clazz);
        }
        catch (JsonParseException jpe)
        {
            LazyLogger.e(jpe, String.format("反序列化失败, 错误原因:%s", jpe.getMessage()));
        }
        catch (JsonMappingException jme)
        {
        	LazyLogger.e(jme,String.format("反序列化失败, 错误原因:%s", jme.getMessage()));
        }
        catch (IOException ioe)
        {
        	LazyLogger.e(ioe, String.format("反序列化失败, 错误原因:%s", ioe.getMessage()));
        }
        catch (NullPointerException e)
        {
        	LazyLogger.e(e, String.format("反序列化失败, 错误原因:%s", e.getMessage()));
        }catch (Exception e)
        {
        	LazyLogger.e(e, String.format("反序列化失败, 错误原因:%s", e.getMessage()));
        }
        return null;
    }
}
