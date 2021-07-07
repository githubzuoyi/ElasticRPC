package org.apache.elasticrpc.server.annotation;

import org.apache.elasticrpc.server.RpcClient;

/**
 *
 * 适配 spring annotation Rpc 实现 {@link RpcClient}
 *
 * @author: feizuo
 * @createDate: 2021-07-06 20:37
 * @since: 1.0.0
 */
public class AnnotationRpcClient implements RpcClient{


    @Override
    public void close() throws Exception {

    }
}
