package org.apache.elasticrpc.serialization;

import java.io.*;

/**
 * jdk standard serialization
 *
 * @author: feizuo
 * @since: 1.0.0
 * @see ByteArrayOutputStream
 * @see ObjectOutputStream
 * @see ByteArrayInputStream
 * @see ObjectInputStream
 */
public class DefaultSerializer implements Serializer<Object> {

    @Override
    public byte[] serialize(Object source) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(source);
        objectOutputStream.flush();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return bytes;
    }

    @Override
    public Object Deserialize(byte[] source, Class<?> type) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(source);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object objsource = objectInputStream.readObject();
        return objsource;
    }
}
