package org.mvnsearch.rsocket.requester;

import io.rsocket.metadata.WellKnownMimeType;
import org.mvnsearch.account.AccountService;
import org.mvnsearch.account.UserService;
import org.mvnsearch.spring.boot.rsocket.RSocketRemoteServiceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.net.URI;

/**
 * rsocket configuration
 *
 * @author linux_china
 */
@Configuration
public class RSocketConfiguration {

    @Bean
    public RSocketRequester rsocketRequester(RSocketStrategies strategies) {
        return RSocketRequester.builder()
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .metadataMimeType(MimeType.valueOf(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString()))
                .rsocketStrategies(strategies)
                .websocket(URI.create("ws://127.0.0.1:8088/rsocket"));
    }

    @Bean
    public AccountService accountService(RSocketRequester rsocketRequester) {
        return RSocketRemoteServiceBuilder.client(rsocketRequester, AccountService.class).build();
    }

    @Bean
    public UserService userService(RSocketRequester rsocketRequester) {
        return RSocketRemoteServiceBuilder.client(rsocketRequester, UserService.class).build();
    }
}
