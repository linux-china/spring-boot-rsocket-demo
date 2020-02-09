package org.mvnsearch.rsocket.responder;

import com.github.javafaker.Faker;
import org.mvnsearch.account.User;
import org.mvnsearch.account.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

/**
 * User Service implementation
 *
 * @author linux_china
 */
@Controller
@MessageMapping("org.mvnsearch.account.UserService")
public class UserServiceImpl implements UserService {
    private Faker faker = new Faker();

    @MessageMapping("findById")
    public Mono<User> findById(Integer id) {
        User user = new User();
        user.setId(id);
        user.setNick(faker.name().name());
        user.setPhone(faker.phoneNumber().cellPhone());
        user.setEmail(faker.internet().emailAddress());
        return Mono.just(user);
    }
}
