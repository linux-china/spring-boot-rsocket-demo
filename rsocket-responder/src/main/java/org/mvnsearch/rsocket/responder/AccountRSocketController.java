package org.mvnsearch.rsocket.responder;

import org.mvnsearch.account.Account;
import org.mvnsearch.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * account rsocket controller
 *
 * @author linux_china
 */
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
