package org.apache.elasticrpc;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * Request 实体
 *
 * @author: feizuo
 * @createDate: 2021-07-06 19:52
 * @since: 1.0.0
 */
public class InvocationRequest implements Serializable {

    private static final long serialVersionUID = -4721607536018568393L;

    private String requestId;

    private String serviceName;

    private String serviceMethod;

    private Object[] parameters;

    private Class[] parameterTypes;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceMethod() {
        return serviceMethod;
    }

    public void setServiceMethod(String serviceMethod) {
        this.serviceMethod = serviceMethod;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }


    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    @Override
    public String toString() {
        return "InvocationRequest{" +
                "requestId='" + requestId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceMethod='" + serviceMethod + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                '}';
    }
}
