package org.mvnsearch.rsocket.requester;

import org.mvnsearch.account.Account;
import org.mvnsearch.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * account controller
 *
 * @author linux_china
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public Mono<Account> show(@PathVariable Integer id) {
        return accountService.findById(id);
    }
}
