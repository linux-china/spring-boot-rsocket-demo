package org.mvnsearch.rsocket.responder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.metadata.CompositeMetadataFlyweight;
import io.rsocket.metadata.TaggingMetadataFlyweight;
import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.uri.UriTransportRegistry;
import io.rsocket.util.DefaultPayload;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


/**
 * rsocket sdk unit test
 *
 * @author linux_china
 */
public class RSocketClientTest {
    private ObjectMapper objectMapper = new ObjectMapper();
    private static RSocket rsocket;

    @BeforeAll
    public static void setUp() throws Exception {
        rsocket = RSocketFactory
                .connect()
                .dataMimeType(WellKnownMimeType.APPLICATION_JSON.getString())
                .metadataMimeType(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString())
                .transport(UriTransportRegistry.clientForUri("ws://127.0.0.1:8088/rsocket"))
                .start()
                .block();
    }

    @AfterAll
    public static void tearDown() {
        rsocket.dispose();
    }

    @Test
    public void testRequestResponse() throws Exception {
        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
        CompositeByteBuf compositeByteBuf = allocator.compositeDirectBuffer();
        ByteBuf routingMetadata = TaggingMetadataFlyweight.createTaggingContent(allocator, Arrays.asList("org.mvnsearch.account.AccountService.findById"));
        CompositeMetadataFlyweight.encodeAndAddMetadata(compositeByteBuf, allocator, WellKnownMimeType.MESSAGE_RSOCKET_ROUTING, routingMetadata);
        rsocket.requestResponse(DefaultPayload.create(Unpooled.wrappedBuffer(objectMapper.writeValueAsBytes(1)), compositeByteBuf))
                .subscribe(payload -> {
                    System.out.println(payload.getDataUtf8());
                });
        Thread.sleep(500000);
    }
}
