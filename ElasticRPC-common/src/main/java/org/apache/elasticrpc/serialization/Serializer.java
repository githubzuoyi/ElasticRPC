package org.apache.elasticrpc.serialization;

import org.apache.elasticrpc.util.ServiceLoaders;

import java.io.IOException;

/**
 * {@link Serializer} 序列化接口
 *
 * @author feizuo
 * @since 1.0.0
 */
public interface Serializer<T> {

    Serializer DEFAULT = ServiceLoaders.load(Serializer.class);

    byte[] serialize(T source) throws IOException;

    T Deserialize(byte[] source, Class<?> type) throws IOException, ClassNotFoundException;

}
