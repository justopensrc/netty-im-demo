package com.netease.music.im.serialize;

/**
 * @author bjfanglong
 * @date 2018/11/16.
 */
public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    byte getSerializerAlgorithm();

    byte[] serialize(Object object);
    
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
