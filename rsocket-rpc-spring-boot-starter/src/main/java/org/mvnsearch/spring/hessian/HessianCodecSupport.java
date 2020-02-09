package org.mvnsearch.spring.hessian;

import com.caucho.hessian.io.HessianSerializerInput;
import com.caucho.hessian.io.HessianSerializerOutput;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Hessian Codec support
 *
 * @author linux_china
 */
public class HessianCodecSupport {
    public static MimeType HESSIAN_MIME_TYPE = MimeTypeUtils.parseMimeType("application/x-hessian");
    public static List<MediaType> HESSIAN_MEDIA_TYPES = Arrays.asList(MediaType.asMediaType(HESSIAN_MIME_TYPE));
    public static List<MimeType> HESSIAN_MIME_TYPES = Arrays.asList(HESSIAN_MIME_TYPE);


    public Object decode(DataBuffer dataBuffer) throws Exception {
        return new HessianSerializerInput(dataBuffer.asInputStream()).readObject();
    }

    public DataBuffer encode(Object obj, DataBufferFactory bufferFactory) throws Exception {
        DataBuffer dataBuffer = bufferFactory.allocateBuffer();
        HessianSerializerOutput output = new HessianSerializerOutput(dataBuffer.asOutputStream());
        output.writeObject(obj);
        output.flush();
        return dataBuffer;
    }
}
