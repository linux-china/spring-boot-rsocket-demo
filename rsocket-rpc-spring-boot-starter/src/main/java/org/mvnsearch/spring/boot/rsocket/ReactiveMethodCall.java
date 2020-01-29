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
    public static int REQUEST_RESPONSE = 0x04;
    public static int REQUEST_FNF = 0x05;
    public static int REQUEST_STREAM = 0x06;
    public static int REQUEST_CHANNEL = 0x07;
    private int requestType;

    public ReactiveMethodCall(Method method) {
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType != null) {
            ParameterizedType aType = (ParameterizedType) method.getGenericReturnType();
            this.returnDataType = (Class<?>) aType.getActualTypeArguments()[0];
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length > 0) {
            if (parameterTypes[0].isAssignableFrom(Flux.class)) {
                this.requestType = REQUEST_CHANNEL;
            }
        }
        if (requestType == 0) {
            if (method.getReturnType().isAssignableFrom(Mono.class)) {
                this.requestType = REQUEST_RESPONSE;
            } else if (method.getReturnType().isAssignableFrom(Flux.class)) {
                this.requestType = REQUEST_STREAM;
            } else {
                this.requestType = REQUEST_FNF;
            }
        }
    }

    public Class<?> getReturnDataType() {
        return returnDataType;
    }

    public int getRequestType() {
        return requestType;
    }
}
