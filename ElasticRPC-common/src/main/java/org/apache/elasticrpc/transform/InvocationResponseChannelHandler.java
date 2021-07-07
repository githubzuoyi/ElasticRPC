package org.apache.elasticrpc.transform;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.elasticrpc.InvocationResponse;
import org.apache.elasticrpc.server.ExchangeFuture;

/**
 *
 * 客户端处理
 *
 * @author: feizuo
 * @since: 1.0.0
 */
public class InvocationResponseChannelHandler extends SimpleChannelInboundHandler<InvocationResponse> {


    /**
     * 接收 服务端返回值，通过 {@link io.netty.util.concurrent.Promise} 异步处理
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InvocationResponse msg) throws Exception {
        String requestId = msg.getRequestId();
        ExchangeFuture future = ExchangeFuture.removeExchangeFuture(requestId);
        if(future != null){
            Object entity = msg.getEntity();
            future.getPromise().setSuccess(entity);
        }
    }

}
