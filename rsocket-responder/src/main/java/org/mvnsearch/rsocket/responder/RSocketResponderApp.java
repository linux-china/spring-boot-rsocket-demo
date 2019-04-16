package org.mvnsearch.rsocket.responder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * rsocket responder app
 *
 * @author linux_china
 */
@SpringBootApplication
public class RSocketResponderApp {
    public static void main(String[] args) {
        SpringApplication.run(RSocketResponderApp.class, args);
    }
}
