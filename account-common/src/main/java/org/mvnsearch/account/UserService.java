package org.mvnsearch.account;

import reactor.core.publisher.Mono;

/**
 * User service
 *
 * @author linux_china
 */
public interface UserService {
    Mono<User> findById(Integer id);
}