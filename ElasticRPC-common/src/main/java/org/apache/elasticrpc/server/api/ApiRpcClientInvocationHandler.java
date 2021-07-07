package org.apache.elasticrpc.server.api;

import io.netty.channel.Channel;
import org.apache.elasticrpc.InvocationRequest;
import org.apache.elasticrpc.registry.ServiceInstance;
import org.apache.elasticrpc.server.ExchangeFuture;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

/**
 * 动态代理
 * 调用 服务端netty 服务，并使用负载均衡能力
 */
public class ApiRpcClientInvocationHandler implements InvocationHandler {

    private final String serviceName;

    private final ApiRpcClient rpcClient;

    public ApiRpcClientInvocationHandler(String serviceName, ApiRpcClient rpcClient) {
        this.serviceName = serviceName;
        this.rpcClient = rpcClient;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 封装request
        InvocationRequest request = createRequest(method, args);

        // 根据负载均衡策略查找服务实例
        List<ServiceInstance> serviceInstances = rpcClient.getServiceInstances();
        ServiceInstance serviceInstance = rpcClient.Select(serviceInstances);

        // 连接netty实例，并发送请求数据
        Channel channel = rpcClient.connect(serviceInstance);
        sendRequest(request,channel);

        ExchangeFuture future = ExchangeFuture.createExchangeFuture(request);

        // 异步获取返回对象
        try{
            return future.get();
        }catch (Exception e){
            String requestId = request.getRequestId();
            ExchangeFuture.removeExchangeFuture(requestId);
        }

        throw new IllegalStateException("invocation failed");

    }

    private void sendRequest(InvocationRequest request, Channel channel) {
        channel.writeAndFlush(request);
    }

    // 构造请求
    private InvocationRequest createRequest(Method method, Object[] args) {
        InvocationRequest request = new InvocationRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setServiceName(method.getDeclaringClass().getName());
        request.setServiceMethod(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        return request;
    }

}
