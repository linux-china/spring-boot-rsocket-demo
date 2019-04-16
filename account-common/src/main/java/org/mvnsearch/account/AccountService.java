package org.mvnsearch.account;

import reactor.core.publisher.Mono;

/**
 * account service
 *
 * @author linux_china
 */
public interface AccountService {

    Mono<Account> findById(Integer id);
}
