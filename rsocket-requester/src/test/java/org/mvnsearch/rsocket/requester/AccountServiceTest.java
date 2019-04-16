package org.mvnsearch.rsocket.requester;

import org.junit.jupiter.api.Test;
import org.mvnsearch.account.Account;
import org.mvnsearch.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

/**
 * account service test
 *
 * @author linux_china
 */
public class AccountServiceTest extends SpringBootBaseTest {
    @Autowired
    private AccountService accountService;

    @Test
    public void testFindById() {
        Mono<Account> account = accountService.findById(1);
        System.out.println(account.block());
    }

}
