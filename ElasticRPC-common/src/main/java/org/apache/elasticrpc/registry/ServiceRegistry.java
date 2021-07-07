package org.apache.elasticrpc.registry;

import org.apache.elasticrpc.util.ServiceLoaders;

import java.util.List;

/**
 *
 * 注册中心抽象
 * 注册/注销 服务
 * 管理 {@link ServiceInstance} 服务实例
 *
 * @author: feizuo
 * @createDate: 2021-07-06 20:13
 * @since: 1.0.0
 */
public interface ServiceRegistry extends AutoCloseable{

    ServiceRegistry DEFAULT = ServiceLoaders.load(ServiceRegistry.class);

    boolean register(ServiceInstance serviceInstance);

    boolean disregister(ServiceInstance serviceInstance);

    List<ServiceInstance> getServiceInstances();
}
