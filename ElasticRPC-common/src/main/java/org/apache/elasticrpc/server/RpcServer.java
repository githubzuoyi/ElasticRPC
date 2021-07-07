package org.apache.elasticrpc.server;

/**
 *
 * Rpc server 抽象
 *
 * @author feizuo
 * @since 1.0.0
 */
public interface RpcServer {

    RpcServer start();

    boolean close();

}
