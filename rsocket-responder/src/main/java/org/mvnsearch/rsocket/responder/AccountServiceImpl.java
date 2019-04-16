package org.mvnsearch.rsocket.responder;

import org.mvnsearch.account.Account;
import org.mvnsearch.account.AccountService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * account service implementation
 *
 * @author linux_china
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public Mono<Account> findById(Integer id) {
        return Mono.just(new Account(id, "nick:" + id));
    }

    @Override
    public Flux<Account> findAll() {
        return Flux.just(new Account(1, "Jackie"), new Account(2, "Tom"));
    }
}
