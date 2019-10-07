Spring Boot RSocket Demo
========================

Demo with Spring Messaging RSocket and Spring Boot 2.2.0.M5(spring-boot-starter-rsocket).

### Requirements

* Jdk 1.8+
* Spring Framework 5.2.0
* Spring Boot 2.2.0.RC1

### Glossary

* RSocketRequester: RSocket Requester, almost like WebClient for HTTP.

### Modules

* account-common: shared library for RSocket responder & requester
* rsocket-responder: supply RSocket services
* rsocket-requester: consume RSocket services
* rsocket-react-demo: call rsocket service from browser

### How the demo works?

* Create a Reactive service interface like following

```java
public interface AccountService {

    Mono<Account> findById(Integer id);

    Flux<Account> findAll();

}
```

* On responder side: implement Service Interface and create a RSocket Controller, for example AccountRSocketController. In future you can use code generation strategy.

```java
@Controller
public class AccountRSocketController {
    @Autowired
    private AccountService accountService;

    @MessageMapping("org.mvnsearch.account.AccountService.findById")
    public Mono<Account> findById(Integer id) {
        return accountService.findById(id);
    }

    @MessageMapping("org.mvnsearch.account.AccountService.findById.{id}")
    public Mono<Account> findById2(@DestinationVariable Integer id) {
        return accountService.findById(id);
    }

    @MessageMapping("org.mvnsearch.account.AccountService.findAll")
    public Flux<Account> findAll() {
        return accountService.findAll();
    }
}
```

* On requester side: use RSocketRemoteServiceBuilder to build service call stub bean.

```java
@Configuration
public class RSocketConfiguration {
    @Bean
    public RSocket rsocket() {
        return RSocketFactory.connect()
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .transport(TcpClientTransport.create("localhost", 42252))
                .start()
                .block();
    }

    @Bean
    public RSocketRequester rsocketRequester(RSocket rSocket, RSocketStrategies strategies) {
        return RSocketRequester.create(rSocket, MimeTypeUtils.APPLICATION_JSON, strategies);
    }


    @Bean
    public AccountService accountService(RSocketRequester rsocketRequester) {
        return RSocketRemoteServiceBuilder.client(rsocketRequester, AccountService.class).build();
    }
}
```

### Browser support

Please refer [rsocket-react-demo](rsocket-react-demo) for RSocket and React integration.


### Changes

* Option to customize or mutate existing RSocketStrategies: https://github.com/spring-projects/spring-framework/issues/22799
* Add coroutines support to RSocket @MessageMapping and RSocketRequester: https://github.com/spring-projects/spring-framework/issues/22780

### References

* Spring RSocket: https://docs.spring.io/spring/docs/5.2.0.RELEASE/spring-framework-reference/web-reactive.html#rsocket
* Spring Boot RSocket: https://docs.spring.io/spring-boot/docs/2.2.0.RC1/reference/html/spring-boot-features.html#boot-features-rsocket
* RSocket: http://rsocket.io/
