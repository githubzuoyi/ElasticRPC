package org.apache.elasticrpc.registry;

import java.util.List;

/**
 *
 * 文件实现注册中心 {@link ServiceRegistry}
 *
 * @author: feizuo
 * @createDate: 2021-07-06 21:00
 * @since: 1.0.0
 */
public class FileServiceRegistry implements ServiceRegistry {


    @Override
    public boolean register(ServiceInstance serviceInstance) {
        return false;
    }

    @Override
    public boolean disregister(ServiceInstance serviceInstance) {
        return false;
    }

    @Override
    public List<ServiceInstance> getServiceInstances() {
        return null;
    }

    @Override
    public void close() {

    }
}
