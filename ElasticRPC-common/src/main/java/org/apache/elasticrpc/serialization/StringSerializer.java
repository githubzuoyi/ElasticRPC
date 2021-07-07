package org.apache.elasticrpc.serialization;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *
 * {@link Serializer} implement based on String conversion
 *
 * @author: feizuo
 * @since: 1.0.0
 */
public class StringSerializer implements Serializer<String> {
    @Override
    public byte[] serialize(String source) throws IOException {
        return source.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String Deserialize(byte[] source, Class<?> type) throws IOException, ClassNotFoundException {
        return new String(source,StandardCharsets.UTF_8);
    }
}
