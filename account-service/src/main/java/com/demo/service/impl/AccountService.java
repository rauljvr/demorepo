package com.demo.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.exceptionshandler.ForbiddenTransferException;
import com.demo.exceptionshandler.ResourceNotFoundException;
import com.demo.model.AccountEntity;
import com.demo.model.CurrencyEntity;
import com.demo.repository.AccountRepository;
import com.demo.repository.CurrencyRepository;
import com.demo.service.IAccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountService implements IAccountService {

	private AccountRepository accountRepository;
	private CurrencyRepository currencyRepository;

	@Autowired
	public AccountService(@Lazy AccountRepository accountRepository, @Lazy CurrencyRepository currencyRepository) {
		this.accountRepository = accountRepository;
		this.currencyRepository = currencyRepository;
	}

	@Override
	public AccountEntity getAccount(String accountName) throws ResourceNotFoundException {

		return accountRepository.findByName(accountName)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountName));
	}

	@Override
	public List<AccountEntity> getAccounts() throws ResourceNotFoundException {

		List<AccountEntity> accounts = accountRepository.findAll();
		if (accounts.isEmpty()) {
			log.warn("No accounts found.");
			throw new ResourceNotFoundException("No accounts found");
		}

		return accounts;
	}

	@Override
	public Page<AccountEntity> getAccountsPagable(Pageable pageRequest) throws ResourceNotFoundException {
		Page<AccountEntity> accounts = accountRepository.findAll(pageRequest);
		if (accounts.isEmpty()) {
			log.warn("No accounts found.");
			throw new ResourceNotFoundException("No accounts found");
		}

		return accounts;
	}

	@Override
	public AccountEntity createAccount(AccountEntity newAccount) {
		
		Optional<CurrencyEntity> currencyEntity = currencyRepository
				.findByCurrencyCode(newAccount.getCurrency().getCurrencyCode());

		Optional<AccountEntity> accountEntity = accountRepository.findByName(newAccount.getName());
		CurrencyEntity currencyToSave = currencyEntity.isPresent() ? currencyEntity.get() : newAccount.getCurrency();
		
		return accountEntity.map(acc -> {
			acc.setCurrency(currencyToSave);
			acc.getMoney().setBalance(newAccount.getMoney().getBalance());
			return accountRepository.save(acc);
		}).orElseGet(() -> {
			newAccount.setCurrency(currencyToSave);
			newAccount.getMoney().setAccount(newAccount);
			return accountRepository.save(newAccount);
		});

		// if (accountEntity.isPresent()) {
		// 		AccountEntity oldAccount = accountEntity.get();
		// 		oldAccount.setCurrency(currencyToSave);
		// 		oldAccount.getMoney().setBalance(newAccount.getMoney().getBalance());
		// 		return accountRepository.save(oldAccount);
		// } else {
		// 		newAccount.getMoney().setAccount(newAccount);
		// 		newAccount.setCurrency(currencyToSave);
		// 		return accountRepository.save(newAccount);
		// }
	}

	@Override
	public AccountEntity transferMoneyAccount(String fromAccount, String toAccount, BigDecimal money)
			throws ResourceNotFoundException, ForbiddenTransferException {

		AccountEntity fromAccountE = accountRepository.findByName(fromAccount)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found: " + fromAccount));

		AccountEntity toAccountE = accountRepository.findByName(toAccount)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found: " + toAccount));

		if (checkTransaction(fromAccountE, toAccountE, money)) {
			fromAccountE.getMoney().setBalance(fromAccountE.getMoney().getBalance().subtract(money));
			toAccountE.getMoney().setBalance(fromAccountE.getMoney().getBalance().add(money));
			accountRepository.save(fromAccountE);
			accountRepository.save(toAccountE);
		} else {
			log.warn("Account balance is not enough. Account: " + fromAccount + " with balance: "
					+ fromAccountE.getMoney().getBalance());
			throw new ForbiddenTransferException("Your current account balance is not enough. Account: " + fromAccount
					+ " with balance: " + fromAccountE.getMoney().getBalance());
		}

		return fromAccountE;
	}

	private boolean checkTransaction(AccountEntity fromAccount, AccountEntity toAccount, BigDecimal money) {

		BigDecimal balance = fromAccount.getMoney().getBalance();
		return (!toAccount.isTreasury() && balance.compareTo(money) < 0 && !fromAccount.isTreasury())
				|| (toAccount.isTreasury() && balance.compareTo(money) < 0 && !fromAccount.isTreasury()) ? false : true;
	}

}
