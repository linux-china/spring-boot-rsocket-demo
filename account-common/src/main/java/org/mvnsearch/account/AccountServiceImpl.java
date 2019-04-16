package org.mvnsearch.account;

import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;

/**
 * account service implementation with spring messaging rsocket requester
 *
 * @author linux_china
 */
public class AccountServiceImpl implements AccountService {
    private RSocketRequester rsocketRequester;

    public AccountServiceImpl(RSocketRequester rsocketRequester) {

        this.rsocketRequester = rsocketRequester;
    }

    @Override
    public Mono<Account> findById(Integer id) {
        return rsocketRequester.route("findById").data(id).retrieveMono(Account.class);
    }
}
