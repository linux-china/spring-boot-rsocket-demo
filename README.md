Spring Boot RSocket Demo
========================

Demo with Spring Messaging RSocket and Spring Boot.

### Glossary

* RSocketRequester: RSocket请求者，你可以理解为WebClient

### Modules

* account-common: shared library for RSocket responder & requester
* rsocket-responder: supply RSocket services
* rsocket-requester: consume RSocket services

### How to use Reactive Service Interface

* Create a service interface with Reactor support, please check AccountService
* On responder side: implement Service Interface and create a RSocket Controller, for example AccountRSocketController. In future you can use code generation.
* On requester side: use RSocketRemoteServiceBuilder to build service call stub.

### References

* Spring Tips: RSocket Messaging in Spring Boot 2.2: https://spring.io/blog/2019/04/15/spring-tips-rsocket-messaging-in-spring-boot-2-2
* RSocket: http://rsocket.io/
