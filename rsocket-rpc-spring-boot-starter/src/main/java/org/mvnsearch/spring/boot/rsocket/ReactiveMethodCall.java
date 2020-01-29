package org.mvnsearch.spring.boot.rsocket;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Reactive method call
 *
 * @author linux_china
 */
public class ReactiveMethodCall {
    private Class<?> returnDataType;
    private int requestType;

    public ReactiveMethodCall(Method method) {
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType != null) {
            ParameterizedType aType = (ParameterizedType) method.getGenericReturnType();
            this.returnDataType = (Class<?>) aType.getActualTypeArguments()[0];
        }
        if (method.getReturnType().isAssignableFrom(Mono.class)) {
            this.requestType = 0x04;
        } else if (method.getReturnType().isAssignableFrom(Flux.class)) {
            this.requestType = 0x06;
        } else {
            this.requestType = 0x05;
        }
    }

    public Class<?> getReturnDataType() {
        return returnDataType;
    }

    public int getRequestType() {
        return requestType;
    }
}
