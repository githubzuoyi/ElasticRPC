package org.apache.elasticrpc.server.api;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.elasticrpc.codec.MessageDecoder;
import org.apache.elasticrpc.codec.MessageEncoder;
import org.apache.elasticrpc.loadbalancer.LoadBalancer;
import org.apache.elasticrpc.registry.FileServiceRegistry;
import org.apache.elasticrpc.registry.ServiceInstance;
import org.apache.elasticrpc.registry.ServiceRegistry;
import org.apache.elasticrpc.server.RpcClient;
import org.apache.elasticrpc.transform.InvocationResponseChannelHandler;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 适配 api Rpc 实现 {@link RpcClient}
 * <p>
 * 构建 Netty 客户端连接池
 *
 * @author: feizuo
 * @createDate: 2021-07-06 20:36
 * @since: 1.0.0
 */
public class ApiRpcClient implements RpcClient {

    private NioEventLoopGroup group;

    private Bootstrap bootstrap;

    private ServiceRegistry serviceRegistry;

    private LoadBalancer loadBalancer;

    // 使用默认
    public ApiRpcClient() {
        this(new FileServiceRegistry(), LoadBalancer.DEFAULT);
    }

    public ApiRpcClient(ServiceRegistry serviceRegistry, LoadBalancer loadBalancer) {
        this.serviceRegistry = serviceRegistry;
        this.loadBalancer = loadBalancer;
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        bootstrap.group(group)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MessageEncoder()) // 使用 自定义Java序列化编解码器
                                .addLast(new MessageDecoder())
                                .addLast(new InvocationResponseChannelHandler());
                    }
                });
    }

    public Channel connect(ServiceInstance instance) {
        String host = instance.getHost();
        int port = instance.getPort();
        return bootstrap.connect(host, port).channel();
    }

    // 获取服务实例
    public List<ServiceInstance> getServiceInstances(){
        return serviceRegistry.getServiceInstances();
    }

    // 负载均衡实例
    public ServiceInstance Select(List<ServiceInstance> serviceInstances){
        return loadBalancer.select(serviceInstances);
    }

    // 获取代理对象
    public Object getProxyInstance(String serviceName, Class<?> interfaceClass) {
        return Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{interfaceClass}, new ApiRpcClientInvocationHandler(serviceName,this));
    }


    @Override
    public void close() throws Exception {
        group.shutdownGracefully();
    }
}
