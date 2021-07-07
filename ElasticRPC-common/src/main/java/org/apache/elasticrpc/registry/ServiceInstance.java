package org.apache.elasticrpc.registry;

/**
 * 服务实例
 *
 * @author: feizuo
 * @createDate: 2021-07-06 20:13
 * @since: 1.0.0
 */
public class ServiceInstance {

    private String id;

    private String applicationName;

    private String host;

    private int port;

    public ServiceInstance(){

    }

    public ServiceInstance(String id, String applicationName, String host, int port) {
        this.id = id;
        this.applicationName = applicationName;
        this.host = host;
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ServiceInstance{" +
                "id='" + id + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
