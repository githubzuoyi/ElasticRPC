package org.apache.elasticrpc;

import java.io.Serializable;

/**
 * Response 实体
 *
 * @author: feizuo
 * @createDate: 2021-07-06 19:53
 * @since: 1.0.0
 */
public class InvocationResponse implements Serializable {

    private static final long serialVersionUID = -4721607536018568393L;

    private String requestId;

    private Object entity;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "InvocationResponse{" +
                "requestId='" + requestId + '\'' +
                ", entity=" + entity +
                '}';
    }
}
