Spring Boot RSocket Demo
========================

Demo with Spring Messaging RSocket and Spring Boot 2.2.0.M2(spring-boot-starter-rsocket).

### Requirements

* Jdk 1.8+
* Spring Framework 5.2.0.M1
* Spring Boot 2.2.0.M2

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

### References

* Spring Tips: RSocket Messaging in Spring Boot 2.2: https://spring.io/blog/2019/04/15/spring-tips-rsocket-messaging-in-spring-boot-2-2
* RSocket: http://rsocket.io/
