package org.mvnsearch.rsocket.requester;

import org.mvnsearch.account.User;
import org.mvnsearch.account.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * user controller
 *
 * @author linux_china
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public Mono<User> show(@PathVariable Integer id) {
        return userService.findById(id);
    }
}
