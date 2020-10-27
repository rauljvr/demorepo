package com.demo.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.demo.exceptionshandler.ForbiddenTransferException;
import com.demo.exceptionshandler.ResourceNotFoundException;
import com.demo.model.AccountEntity;

public interface IAccountService {

	public AccountEntity getAccount(String name) throws ResourceNotFoundException;

	public AccountEntity createAccount(AccountEntity acount);

	public AccountEntity transferMoneyAccount(String fromAccount, String toAccount, BigDecimal money)
			throws ResourceNotFoundException, ForbiddenTransferException;

	public List<AccountEntity> getAccounts() throws ResourceNotFoundException;

	public Page<AccountEntity> getAccountsPagable(Pageable pageRequest) throws ResourceNotFoundException;

}
