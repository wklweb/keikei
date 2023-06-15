package com.keikei.framework.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import org.apache.commons.lang3.CharUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    private Class<T> tClass;
    public FastJson2JsonRedisSerializer(Class tClass) {
        super();
        this.tClass = tClass;
    }

    /**
     * 序列化
     * @param o
     * @return
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(T o) throws SerializationException {
        if(o == null){
            return new byte[0];
        }
        return JSON.toJSONString(o, JSONWriter.Feature.WriteClassName).getBytes(Charset.forName("UTF-8"));
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     * @throws SerializationException
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if(bytes == null || bytes.length <= 0){
            return null;
        }
        String obj = new String(bytes,Charset.forName("UTF-8"));
        return JSON.parseObject(obj,tClass,JSONReader.Feature.SupportAutoType);
    }
}
