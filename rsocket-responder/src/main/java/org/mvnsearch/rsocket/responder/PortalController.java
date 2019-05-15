package org.mvnsearch.rsocket.responder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * portal controller
 *
 * @author linux_china
 */
@RestController
public class PortalController {

    @RequestMapping("/welcome")
    public Mono<String> welcome() {
        return  Mono.just("Welcome");
    }
}
