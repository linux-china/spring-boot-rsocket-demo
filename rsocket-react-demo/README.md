RSocket React Demo
==================

React with RSocket(WebSocket) to call Reactive Services.

### How to run demo

* install rsocket-cli
```
brew install yschimke/tap/rsocket-cli
```

* start RSocket WebSocket Server

```
 rsocket-cli -i '{"nick":"nick from rsocket","id":1}' --server ws://localhost:8088/rsocket
```

Or you can start RSocket Spring Boot App.

* Start React App

```
npm start
```

* Visit http://localhost:3000

### FAQ

Compatible with spring-boot-starter-rsocket 2.2.0.M2 

### References

* RSocket JS: https://github.com/rsocket/rsocket-js