package org.apache.elasticrpc.server.api;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.elasticrpc.codec.MessageDecoder;
import org.apache.elasticrpc.codec.MessageEncoder;
import org.apache.elasticrpc.context.ServiceContext;
import org.apache.elasticrpc.registry.ServiceInstance;
import org.apache.elasticrpc.registry.ServiceRegistry;
import org.apache.elasticrpc.server.RpcServer;
import org.apache.elasticrpc.transform.InvocationRequestChannelHandler;

import java.util.UUID;

/**
 * {@link RpcServer} implement based on API
 *
 * @author: feizuo
 * @since: 1.0.0
 */
public class ApiRpcServer implements RpcServer {

    private final String applicationName;

    private final String host;

    private final int port;
    // 服务上下文
    private final ServiceContext serviceContext;
    // 注册中心
    private final ServiceRegistry serviceRegistry;
    // 服务实例
    private final ServiceInstance serviceInstance;
    // netty boss 线程
    private NioEventLoopGroup bossGroup;
    // netty 工作线程
    private NioEventLoopGroup workerGroup;
    // netty server
    private ServerBootstrap serverBootstrap;

    /**
     * 初始化时加载 netty server
     */
    public ApiRpcServer(String applicationName, String host, int port) {
        this.applicationName = applicationName;
        this.host = host;
        this.port = port;
        this.serviceContext = ServiceContext.DEFAULT;
        this.serviceRegistry = ServiceRegistry.DEFAULT;
        this.serviceInstance = createLocalServiceInstance();
        init();
    }

    private ServiceInstance createLocalServiceInstance() {
        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setId(UUID.randomUUID().toString());
        serviceInstance.setApplicationName(applicationName);
        serviceInstance.setHost(host);
        serviceInstance.setPort(port);
        return serviceInstance;
    }

    /**
     * 初始化启动
     */
    public void init() {
        start();
    }

    /**
     * 实例启动，将实例装填进注册中心
     * <p>
     * TODO 硬编码信息通过 外部化配置 读取 - config
     *
     * @return
     */
    @Override
    public RpcServer start() {

        this.bossGroup = new NioEventLoopGroup(1);
        this.workerGroup = new NioEventLoopGroup();
        this.serverBootstrap = new ServerBootstrap();
        this.serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("message-decoder", new MessageDecoder())
                                .addLast("message-encoder", new MessageEncoder())
                                .addLast("request-handler", new InvocationRequestChannelHandler(serviceContext));
                    }
                });

        ChannelFuture future = serverBootstrap.bind(host,port);

        // registry serviceInstance to registry
        registryService();

        try {
            Channel channel = future.sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    private void registryService() {
        serviceRegistry.register(serviceInstance);
    }

    private void disRegistryService() {
        serviceRegistry.disregister(serviceInstance);
    }

    /**
     * 关闭 Netty Server
     *
     * @return
     */
    @Override
    public boolean close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        disRegistryService();
        return true;
    }
}
