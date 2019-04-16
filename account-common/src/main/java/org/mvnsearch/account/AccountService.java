package org.mvnsearch.account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * account service
 *
 * @author linux_china
 */
public interface AccountService {

    Mono<Account> findById(Integer id);

    Flux<Account> findAll();

}
