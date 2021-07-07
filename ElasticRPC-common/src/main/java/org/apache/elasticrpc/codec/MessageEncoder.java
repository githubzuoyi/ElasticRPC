package org.apache.elasticrpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.elasticrpc.serialization.Serializer;

/**
 *
 * Netty 消息编码器
 *
 * @see MessageToByteEncoder
 * @see org.apache.elasticrpc.serialization.Serializer
 *
 * @author: feizuo
 * @createDate: 2021-07-06
 * @since: 1.0.0
 */
public class MessageEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        Serializer serializer = Serializer.DEFAULT;
        byte[] bytes = serializer.serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
