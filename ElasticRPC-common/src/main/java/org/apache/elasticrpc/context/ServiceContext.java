package org.apache.elasticrpc.context;

import org.apache.elasticrpc.util.ServiceLoaders;

/**
 *
 *  服务上下文 - 单例对象
 *  保存可供暴露的实例对象
 *
 *
 * @author: feizuo
 * @since: 1.0.0
 */
public interface ServiceContext {

    /**
     * spi机制 加载默认的 {@link ServiceContext}
     */
    ServiceContext DEFAULT = ServiceLoaders.load(ServiceContext.class);

    boolean registerService(String serviceName,Object ServiceEntity);

    boolean disRegistryService(String serviceName);

    Object getService(String serviceName);

}
