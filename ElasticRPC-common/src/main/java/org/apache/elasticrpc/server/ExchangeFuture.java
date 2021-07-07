package org.apache.elasticrpc.server;

import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import org.apache.elasticrpc.InvocationRequest;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 异步数据交互门面
 *
 * @author: feizuo
 * @since: 1.0.0
 * @see java.util.concurrent.Future
 * @see io.netty.util.concurrent.Promise
 */
public class ExchangeFuture implements Future {

    private final InvocationRequest request;

    private final Promise<Object> promise;

    static Map<String, ExchangeFuture> exchangeFutureMap = new ConcurrentHashMap<>();

    public static ExchangeFuture createExchangeFuture(final InvocationRequest request) {
        String requestId = request.getRequestId();
        return exchangeFutureMap.computeIfAbsent(requestId, key -> new ExchangeFuture(request));
    }

    public static ExchangeFuture removeExchangeFuture(String requestId) {
        return exchangeFutureMap.remove(requestId);
    }

    public ExchangeFuture(InvocationRequest request) {
        this.request = request;
        this.promise = new DefaultPromise<>(new DefaultEventExecutor());
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return promise.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return promise.isCancelled();
    }

    @Override
    public boolean isDone() {
        return promise.isDone();
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return promise.get();
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return promise.get(timeout, unit);
    }

    public InvocationRequest getRequest() {
        return this.request;
    }

    public Promise getPromise() {
        return this.promise;
    }
}
