package org.mvnsearch.spring.boot.rsocket;

import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.rsocket.frame.FrameType.*;

/**
 * RSocket reactive service proxy
 *
 * @author linux_china
 */
public class RSocketReactiveServiceProxy implements InvocationHandler {
    private Map<Method, ReactiveMethodCall> reactiveCalls = new ConcurrentHashMap<>();
    private RSocketRequester rsocketRequester;
    private String serviceFullName;
    private Duration timeout;

    public RSocketReactiveServiceProxy(RSocketRequester rsocketRequester, Class<?> serviceInterface, Duration timeout) {
        this.rsocketRequester = rsocketRequester;
        this.serviceFullName = serviceInterface.getCanonicalName();
        this.timeout = timeout;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String routeKey = serviceFullName + "." + method.getName();
        ReactiveMethodCall reactiveCall = reactiveCalls.get(method);
        if (reactiveCall == null) {
            reactiveCall = new ReactiveMethodCall(method);
            reactiveCalls.put(method, reactiveCall);
        }
        RSocketRequester.RetrieveSpec retrieveSpec;
        if (args != null && args.length >= 1) {
            retrieveSpec = rsocketRequester.route(routeKey).data(args[0]);
        } else {
            retrieveSpec = rsocketRequester.route(routeKey).data(Mono.empty());
        }
        if (reactiveCall.getFrameType() == REQUEST_RESPONSE) {
            return retrieveSpec.retrieveMono(reactiveCall.getReturnDataType()).timeout(timeout);
        } else if (reactiveCall.getFrameType() == REQUEST_STREAM || reactiveCall.getFrameType() == REQUEST_CHANNEL) {
            return retrieveSpec.retrieveFlux(reactiveCall.getReturnDataType()).timeout(timeout);
        } else {
            return retrieveSpec.send().timeout(timeout);
        }
    }
}
