package org.apache.elasticrpc.util;

import java.util.ServiceLoader;

/**
 *
 * {@link ServiceLoader} 工具类
 *
 * 核心扩展点，通过 spi机制 实现扩展性
 *
 * @author feizuo
 * @since 1.0.0
 */
public class ServiceLoaders {

    public static<T> T load(Class<T> serviceClass){
        return ServiceLoader.load(serviceClass).iterator().next();
    }
}
