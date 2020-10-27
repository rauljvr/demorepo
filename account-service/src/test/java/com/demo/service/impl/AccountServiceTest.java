package com.demo.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.exceptionshandler.ForbiddenTransferException;
import com.demo.model.AccountEntity;
import com.demo.model.CurrencyEntity;
import com.demo.model.MoneyEntity;
import com.demo.repository.AccountRepository;
import com.demo.repository.CurrencyRepository;
import com.demo.service.impl.AccountService;

@RunWith(SpringRunner.class)
public class AccountServiceTest {

	@Mock
	private AccountRepository mockAccountRepository;
	@Mock
	private CurrencyRepository mockCurrencyRepository;

	private AccountService accountService;
	private AccountEntity accountFrom;
	private AccountEntity accountTo;

	private static final String FROM_ACCOUNT = "savings1";
	private static final String TO_ACCOUNT = "savings2";
	private static final String CURRENCY = "EUR";

	@Before
	public void init() {
		accountService = new AccountService(mockAccountRepository, mockCurrencyRepository);

		CurrencyEntity currency1 = new CurrencyEntity(CURRENCY);
		MoneyEntity balance1 = new MoneyEntity(new BigDecimal("120.00"));
		CurrencyEntity currency2 = new CurrencyEntity(CURRENCY);
		MoneyEntity balance2 = new MoneyEntity(new BigDecimal("180.00"));

		accountFrom = new AccountEntity(FROM_ACCOUNT, currency1, balance1, false);
		accountTo = new AccountEntity(TO_ACCOUNT, currency2, balance2, false);

		when(mockAccountRepository.findByName(FROM_ACCOUNT)).thenReturn(Optional.of(accountFrom));
		when(mockAccountRepository.findByName(TO_ACCOUNT)).thenReturn(Optional.of(accountTo));
		when(mockAccountRepository.save(accountFrom)).thenReturn(accountFrom);
	}

	@Test(expected = ForbiddenTransferException.class)
	public void whenAccountToIsNotTreasuryAndAccountFromNegativeBalanceAndNotTreasury_thenMoneyIsBlocked()
			throws IOException, Exception {

		accountService.transferMoneyAccount(FROM_ACCOUNT, TO_ACCOUNT, new BigDecimal("150.00"));
	}

	@Test
	public void whenAccountToIsNotTreasuryAndAccountFromNegativeBalanceAndTreasury_thenMoneyIsTransfered()
			throws IOException, Exception {

		accountFrom.setTreasury(true);
		when(mockAccountRepository.findByName(FROM_ACCOUNT)).thenReturn(Optional.of(accountFrom));

		AccountEntity acc = accountService.transferMoneyAccount(FROM_ACCOUNT, TO_ACCOUNT, new BigDecimal("150.00"));
		assertNotNull(acc);
		assertEquals(new BigDecimal("-30.00"), acc.getMoney().getBalance());
	}

	@Test(expected = ForbiddenTransferException.class)
	public void whenAccountToIsTreasuryAndAccountFromNegativeBalanceAndNotTreasury_thenTransferIsBlocked()
			throws IOException, Exception {

		accountTo.setTreasury(true);
		when(mockAccountRepository.findByName(FROM_ACCOUNT)).thenReturn(Optional.of(accountFrom));

		accountService.transferMoneyAccount(FROM_ACCOUNT, TO_ACCOUNT, new BigDecimal("150.00"));
	}

	@Test
	public void whenAccountToIsTreasuryAndAccountFromNegativeBalanceAndTreasury_thenMoneyIsTransfered()
			throws IOException, Exception {

		accountTo.setTreasury(true);
		accountFrom.setTreasury(true);
		when(mockAccountRepository.findByName(FROM_ACCOUNT)).thenReturn(Optional.of(accountFrom));

		AccountEntity acc = accountService.transferMoneyAccount(FROM_ACCOUNT, TO_ACCOUNT, new BigDecimal("150.00"));
		assertNotNull(acc);
		assertEquals(new BigDecimal("-30.00"), acc.getMoney().getBalance());
	}

	@Test
	public void whenAccountToIsNotTreasuryAndAccountFromPositiveBalanceAndNotTreasury_thenMoneyIsTransfered()
			throws IOException, Exception {

		AccountEntity acc = accountService.transferMoneyAccount(FROM_ACCOUNT, TO_ACCOUNT, new BigDecimal("80.00"));
		assertNotNull(acc);
		assertEquals(new BigDecimal("40.00"), acc.getMoney().getBalance());
	}

	@Test
	public void whenAccountToIsTreasuryAndAccountFromPositiveBalanceAndNotTreasury_thenMoneyIsTransfered()
			throws IOException, Exception {

		accountTo.setTreasury(true);
		when(mockAccountRepository.findByName(FROM_ACCOUNT)).thenReturn(Optional.of(accountFrom));

		AccountEntity acc = accountService.transferMoneyAccount(FROM_ACCOUNT, TO_ACCOUNT, new BigDecimal("80.00"));
		assertNotNull(acc);
		assertEquals(new BigDecimal("40.00"), acc.getMoney().getBalance());
	}

}
