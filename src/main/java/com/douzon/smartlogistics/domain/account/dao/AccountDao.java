package com.douzon.smartlogistics.domain.account.dao;

import com.douzon.smartlogistics.domain.account.dao.mapper.AccountMapper;
import com.douzon.smartlogistics.domain.account.dto.AccountInsertDto;
import com.douzon.smartlogistics.domain.account.dto.AccountModifyDto;
import com.douzon.smartlogistics.domain.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AccountDao {
    private final AccountMapper accountMapper;
    public List<Account> searchAccountList(Integer accountNo, String accountName, String createDate, String createId) {
        return accountMapper.searchAccountList(accountNo, accountName, createDate, createId);
    }

    public void insert(AccountInsertDto accountInsertDto) {
        accountMapper.insert(accountInsertDto);
    }

    public void modify(Integer accountNo, AccountModifyDto accountModifyDto) {
        accountMapper.modify(accountNo, accountModifyDto);
    }

    public void delete(List<Integer> accountNos) {
        retrieveItem(accountNos);
        accountMapper.delete(accountNos);
    }

    private void retrieveItem(List<Integer> accountNos) {
        for (Integer item:accountNos) {
            accountMapper.retrieve(item).orElseThrow((() -> {
                throw new NoSuchElementException("해당 거래처는 존재하지 않습니다.");
            }));
        }
    }

    public boolean checkAccountCode(String accountCode) {
        return accountMapper.checkAccountCode(accountCode) != null;
    }
}
