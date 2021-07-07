package org.apache.elasticrpc.transform;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.elasticrpc.InvocationRequest;
import org.apache.elasticrpc.InvocationResponse;
import org.apache.elasticrpc.context.ServiceContext;
import sun.reflect.misc.MethodUtil;

import java.lang.reflect.Method;

/**
 *
 * 服务端处理
 *
 * 通过反射实现服务端本地方法调用并返回
 *
 * @author: feizuo
 * @since: 1.0.0
 */
public class InvocationRequestChannelHandler extends SimpleChannelInboundHandler<InvocationRequest> {


    private final ServiceContext serviceContext;

    public InvocationRequestChannelHandler(ServiceContext serviceContext){
        this.serviceContext = serviceContext;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InvocationRequest msg) throws Exception {
        // 根据服务上下文 获取 可用调用的方法实例
        Object serviceEntity = serviceContext.getService(msg.getServiceName());
        // 通过反射调用实例方法
        Class<?> interfaceClass = Class.forName(msg.getServiceName());
        Method invokeMethod = MethodUtil.getMethod(interfaceClass,msg.getServiceMethod(),msg.getParameterTypes());
        Object entity = MethodUtil.invoke(invokeMethod,serviceEntity,msg.getParameters());

        // TODO logger 记录 netty server read 情况

        // 封装 response 并回写 响应结果
        InvocationResponse response = new InvocationResponse();
        response.setEntity(entity);
        ctx.writeAndFlush(response);
    }
}
