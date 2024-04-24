package com.douzon.smartlogistics.domain.account.application;

import com.douzon.smartlogistics.domain.account.dto.AccountInsertDto;
import com.douzon.smartlogistics.domain.account.dto.AccountModifyDto;
import com.douzon.smartlogistics.domain.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.douzon.smartlogistics.domain.account.dao.AccountDao;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountDao accountDao;
    public List<Account> searchAccoutList(Integer accountNo, String accountName, String createDate, String createId) {
        return accountDao.searchAccountList(accountNo, accountName, createDate, createId);
    }

    public void insert(AccountInsertDto accountInsertDto) {
        accountDao.insert(accountInsertDto);
    }

    public void modify(Integer accountNo, AccountModifyDto accountModifyDto) {
        accountDao.modify(accountNo, accountModifyDto);
    }

    public void delete(List<Integer> accountNos) {
        accountDao.delete(accountNos);
    }

    public boolean checkAccountCode(String accountCode) {
        return accountDao.checkAccountCode(accountCode);
    }
}
