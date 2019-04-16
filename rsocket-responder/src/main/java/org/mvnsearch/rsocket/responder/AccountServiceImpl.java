package org.mvnsearch.rsocket.responder;

import org.mvnsearch.account.Account;
import org.mvnsearch.account.AccountService;
import reactor.core.publisher.Mono;

/**
 * account service implementation
 *
 * @author linux_china
 */
public class AccountServiceImpl implements AccountService {
    @Override
    public Mono<Account> findById(Integer id) {
        return Mono.just(new Account(id, "nick:" + id));
    }
}
