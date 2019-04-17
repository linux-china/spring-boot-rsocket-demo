Spring Boot RSocket Demo
========================

Demo with Spring Messaging RSocket and Spring Boot(spring-boot-starter-rsocket).

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

### How to use Reactive Service Interface

* Create a service interface with Reactor support, please check AccountService
* On responder side: implement Service Interface and create a RSocket Controller, for example AccountRSocketController. In future you can use code generation.
* On requester side: use RSocketRemoteServiceBuilder to build service call stub.

### References

* Spring Tips: RSocket Messaging in Spring Boot 2.2: https://spring.io/blog/2019/04/15/spring-tips-rsocket-messaging-in-spring-boot-2-2
* RSocket: http://rsocket.io/
