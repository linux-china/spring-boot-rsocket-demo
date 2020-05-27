package org.mvnsearch.rsocket.responder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.metadata.CompositeMetadataCodec;
import io.rsocket.metadata.TaggingMetadataCodec;
import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import io.rsocket.util.DefaultPayload;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Collections;


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
        rsocket = RSocketConnector.create()
                .dataMimeType(WellKnownMimeType.APPLICATION_JSON.getString())
                .metadataMimeType(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString())
                .connect(WebsocketClientTransport.create(URI.create("ws://127.0.0.1:8088/rsocket")))
                .block();
    }

    @AfterAll
    public static void tearDown() {
        rsocket.dispose();
    }

    @Test
    public void testRequestResponse() throws Exception {
        CompositeByteBuf compositeByteBuf = compositeMetadataWithRouting("org.mvnsearch.account.AccountService.findById");
        byte[] jsonData = objectMapper.writeValueAsBytes(1);
        rsocket.requestResponse(DefaultPayload.create(Unpooled.wrappedBuffer(jsonData), compositeByteBuf))
                .subscribe(payload -> {
                    System.out.println(payload.getDataUtf8());
                });
        Thread.sleep(500000);
    }

    private CompositeByteBuf compositeMetadataWithRouting(String routingKey) {
        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
        CompositeByteBuf compositeByteBuf = allocator.compositeDirectBuffer();
        ByteBuf routingMetadata = TaggingMetadataCodec.createTaggingContent(allocator, Collections.singletonList(routingKey));
        CompositeMetadataCodec.encodeAndAddMetadata(compositeByteBuf, allocator, WellKnownMimeType.MESSAGE_RSOCKET_ROUTING, routingMetadata);
        return compositeByteBuf;
    }
}
