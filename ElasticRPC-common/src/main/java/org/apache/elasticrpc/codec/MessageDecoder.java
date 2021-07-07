package org.apache.elasticrpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.elasticrpc.serialization.Serializer;

import java.util.List;

/**
 * Netty 消息解码器
 *
 * @author: feizuo
 * @createDate: 2021-07-06 21:03
 * @since: 1.0.0
 */
public class MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        int length = in.readInt();
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        Serializer serializer = Serializer.DEFAULT;
        Object obj = serializer.Deserialize(bytes,null);
        out.add(obj);
    }
}
