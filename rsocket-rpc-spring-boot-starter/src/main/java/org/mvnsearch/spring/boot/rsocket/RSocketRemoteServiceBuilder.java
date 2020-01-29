package org.mvnsearch.spring.boot.rsocket;

import org.springframework.messaging.rsocket.RSocketRequester;

import java.lang.reflect.Proxy;
import java.time.Duration;

/**
 * rsocket remove service builder
 *
 * @author linux_china
 */
public class RSocketRemoteServiceBuilder<T> {
    private Class<T> serviceInterface;
    private RSocketRequester rsocketRequester;
    private Duration timeout = Duration.ofSeconds(3);

    public static <T> RSocketRemoteServiceBuilder<T> requester(RSocketRequester rsocketRequester) {
        RSocketRemoteServiceBuilder<T> builder = new RSocketRemoteServiceBuilder<T>();
        builder.rsocketRequester = rsocketRequester;
        return builder;
    }

    public static <T> RSocketRemoteServiceBuilder<T> client(RSocketRequester rsocketRequester, Class<T> serviceInterface) {
        RSocketRemoteServiceBuilder<T> builder = new RSocketRemoteServiceBuilder<T>();
        builder.rsocketRequester = rsocketRequester;
        builder.serviceInterface = serviceInterface;
        return builder;
    }

    public RSocketRemoteServiceBuilder<T> serviceInterface(Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
        return this;
    }

    public RSocketRemoteServiceBuilder<T> timeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    @SuppressWarnings("unchecked")
    public T build() {
        return (T) Proxy.newProxyInstance(
                serviceInterface.getClassLoader(),
                new Class[]{serviceInterface},
                new RSocketReactiveServiceProxy(rsocketRequester, serviceInterface, timeout));
    }
}
