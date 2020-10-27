package com.demo;

import java.math.BigDecimal;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.demo.model.AccountEntity;
import com.demo.model.CurrencyEntity;
import com.demo.model.MoneyEntity;
import com.demo.repository.AccountRepository;
import com.demo.repository.CurrencyRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class AccountServiceApplication implements CommandLineRunner {

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private AccountRepository accountRepository;

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		log.info("Starting application...");

		CurrencyEntity euro = new CurrencyEntity("EUR");
		CurrencyEntity usd = new CurrencyEntity("USD");

		AccountEntity acc1 = new AccountEntity("savings1", euro, false);
		AccountEntity acc2 = new AccountEntity("savings2", usd, false);
		AccountEntity acc3 = new AccountEntity("checking1", euro, false);

		MoneyEntity balanceAcc1 = new MoneyEntity(new BigDecimal("120.00"), acc1);
		MoneyEntity balanceAcc2 = new MoneyEntity(new BigDecimal("150.40"), acc2);
		MoneyEntity balanceAcc3 = new MoneyEntity(new BigDecimal("50.00"), acc3);

		euro.setAccounts(Arrays.asList(acc1, acc3));
		usd.setAccounts(Arrays.asList(acc2));
		acc1.setMoney(balanceAcc1);
		acc2.setMoney(balanceAcc2);
		acc3.setMoney(balanceAcc3);

		currencyRepository.save(euro);
		currencyRepository.save(usd);

		log.info("Showing info of the Account {}: ", acc1.getName());

		accountRepository.findByName(acc1.getName()).ifPresent(
				acc -> log.info("balance: {} {} ", acc.getMoney().getBalance(), acc.getCurrency().getCurrencyCode()));

		log.info("Showing all accounts:");
		accountRepository.findAll().forEach(acc -> log.info(acc.getName()));
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
