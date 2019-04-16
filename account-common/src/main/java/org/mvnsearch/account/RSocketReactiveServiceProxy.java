package org.mvnsearch.account;

import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Duration;

/**
 * RSocket reactive service proxy
 *
 * @author linux_china
 */
public class RSocketReactiveServiceProxy implements InvocationHandler {
    private RSocketRequester rsocketRequester;
    private String serviceFullName;
    private Duration timeout;

    public RSocketReactiveServiceProxy(RSocketRequester rsocketRequester, Class serviceInterface, Duration timeout) {
        this.rsocketRequester = rsocketRequester;
        this.serviceFullName = serviceInterface.getCanonicalName();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String routeKey = serviceFullName + "." + method.getName();
        if (method.getReturnType().isAssignableFrom(Mono.class)) {
            return rsocketRequester.route(routeKey).data(args[0]).retrieveMono(args[0].getClass()).timeout(timeout);
        } else if (method.getReturnType().isAssignableFrom(Mono.class)) {
            return rsocketRequester.route(routeKey).data(args[0]).retrieveFlux(args[0].getClass()).timeout(timeout);
        } else {
            return rsocketRequester.route(routeKey).data(args).send().timeout(timeout);
        }
    }
}
