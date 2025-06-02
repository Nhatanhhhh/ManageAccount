package com.ra.service.account;

import com.ra.model.entity.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();
    Account save(Account account);
    Account findById(Long id);
    void delete(Long id);

}
