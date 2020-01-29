package org.mvnsearch.spring.boot.rsocket;

import io.rsocket.frame.FrameType;
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
    private FrameType frameType;

    public ReactiveMethodCall(Method method) {
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType != null) {
            ParameterizedType aType = (ParameterizedType) method.getGenericReturnType();
            this.returnDataType = (Class<?>) aType.getActualTypeArguments()[0];
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length > 0) {
            if (parameterTypes[0].isAssignableFrom(Flux.class)) {
                this.frameType = FrameType.REQUEST_CHANNEL;
            }
        }
        if (frameType == null) {
            if (method.getReturnType().isAssignableFrom(Mono.class)) {
                this.frameType = FrameType.REQUEST_RESPONSE;
            } else if (method.getReturnType().isAssignableFrom(Flux.class)) {
                this.frameType = FrameType.REQUEST_STREAM;
            } else {
                this.frameType = FrameType.REQUEST_FNF;
            }
        }
    }

    public Class<?> getReturnDataType() {
        return returnDataType;
    }

    public FrameType getFrameType() {
        return frameType;
    }
}
