package org.apache.elasticrpc.loadbalancer;

import org.apache.elasticrpc.registry.ServiceInstance;
import org.apache.elasticrpc.util.ServiceLoaders;

import java.util.List;

/**
 * 客户端服务实例负载均衡
 */
public interface LoadBalancer {

    LoadBalancer DEFAULT = ServiceLoaders.load(LoadBalancer.class);

    LoadBalancer getSelector();

    ServiceInstance select(List<ServiceInstance> serviceInstances);

}
