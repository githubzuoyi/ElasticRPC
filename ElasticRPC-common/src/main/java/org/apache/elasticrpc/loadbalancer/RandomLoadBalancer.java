package org.apache.elasticrpc.loadbalancer;

import org.apache.elasticrpc.registry.ServiceInstance;

import java.util.List;

/**
 *
 * 随机负载均衡器 {@link LoadBalancer}
 *
 * @author: feizuo
 * @createDate: 2021-07-06 20:23
 * @since: 1.0.0
 */
public class RandomLoadBalancer implements LoadBalancer{


    @Override
    public LoadBalancer getSelector() {
        return null;
    }

    @Override
    public ServiceInstance select(List<ServiceInstance> serviceInstances) {
        return null;
    }
}
