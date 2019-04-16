package org.mvnsearch.rsocket.responder;

import org.mvnsearch.account.Account;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * account rsocket controller
 *
 * @author linux_china
 */
@Controller
public class AccountRSocketController {

    @MessageMapping("findById")
    public Account findById(Integer id) {
        Account account = new Account();
        account.setId(id);
        account.setNick("linux_china");
        return account;
    }
}
