package org.apache.elasticrpc.context;

/**
 * @author: feizuo
 * @since: 1.0.0
 */
public class RedisServiceContext implements ServiceContext {

    @Override
    public boolean registerService(String serviceName, Object ServiceEntity) {
        return false;
    }

    @Override
    public boolean disRegistryService(String serviceName) {
        return false;
    }

    @Override
    public Object getService(String serviceName) {
        return null;
    }
}
