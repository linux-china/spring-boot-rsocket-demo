package org.mvnsearch.spring.hessian;

import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageEncoder;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

/**
 * Hessian encoder
 *
 * @author linux_china
 */
public class HessianEncoder extends HessianCodecSupport implements HttpMessageEncoder<Object> {

    @Override
    public List<MediaType> getStreamingMediaTypes() {
        return HESSIAN_MEDIA_TYPES;
    }

    @Override
    public boolean canEncode(ResolvableType elementType, MimeType mimeType) {
        return HESSIAN_MIME_TYPE.equals(mimeType);
    }

    @Override
    public Flux<DataBuffer> encode(Publisher<?> inputStream, DataBufferFactory bufferFactory, ResolvableType elementType, MimeType mimeType, Map<String, Object> hints) {
        return Flux.from(inputStream)
                .handle((obj, sink) -> {
                    try {
                        sink.next(encode(obj, bufferFactory));
                    } catch (Exception e) {
                        sink.error(e);
                    }
                });
    }

    @Override
    public List<MimeType> getEncodableMimeTypes() {
        return HESSIAN_MIME_TYPES;
    }
}
