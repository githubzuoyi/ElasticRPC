package org.apache.elasticrpc.context;

/**
 * @author: feizuo
 * @createDate: 2021-07-06 20:31
 * @since: 1.0.0
 */
public class ImMemoryServiceContext implements ServiceContext{


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
