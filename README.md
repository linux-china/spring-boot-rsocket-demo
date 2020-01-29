Spring Boot RSocket Demo
========================

Spring Boot RSocket communication with RPC(interface) style.

# How it works?

* Create a Reactive service interface alike following

```java
public interface AccountService {

    Mono<Account> findById(Integer id);

    Flux<Account> findAll();

}
```

* On responder side: implement Service Interface and annotate it with @Controller and @MessageMapping to make it exposed as RSocket service.

```java
@Service
@Controller
@MessageMapping("org.mvnsearch.account.AccountService")
public class AccountServiceImpl implements AccountService {
    @Override
    @MessageMapping("findById")
    public Mono<Account> findById(Integer id) {
        return Mono.just(new Account(id, "nick:" + id));
    }

    @Override
    @MessageMapping("findAll")
    public Flux<Account> findAll() {
        return Flux.just(new Account(1, "Jackie"), new Account(2, "Tom"));
    }
}
```

* On requester side: use RSocketRemoteServiceBuilder to build service call stub bean.

```java
@Configuration
public class RSocketConfiguration {

    @Bean
    public RSocketRequester rsocketRequester(RSocketStrategies strategies) {
        return RSocketRequester.builder()
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .rsocketStrategies(strategies)
                .connectWebSocket(URI.create("ws://127.0.0.1:8088/rsocket")).block();
    }

    @Bean
    public AccountService accountService(RSocketRequester rsocketRequester) {
        return RSocketRemoteServiceBuilder.client(rsocketRequester, AccountService.class).build();
    }
}
```

# Browser support

Please refer [rsocket-react-demo](rsocket-react-demo) for RSocket and React integration.

# Development

### Requirements

* Jdk 1.8+
* Spring Boot 2.2.4

### Maven Modules

* account-common: Reactive Service API
* rsocket-rpc-spring-boot-starter: Spring Boot Starter to make RSocket call as RPC style
* rsocket-responder: supply RSocket services
* rsocket-requester: consume RSocket services
* rsocket-react-demo: call rsocket service from browser

### RSocket acceptor & handler registry

* acceptor: RSocketMessageHandler.createResponder()
* all @MessageMapping methods: rsocketMessageHandler.getHandlerMethods()

# References

* Spring RSocket: https://docs.spring.io/spring/docs/5.2.3.RELEASE/spring-framework-reference/web-reactive.html#rsocket
* Spring Boot RSocket: https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/html/spring-boot-features.html#boot-features-rsocket
* RSocket: http://rsocket.io/
