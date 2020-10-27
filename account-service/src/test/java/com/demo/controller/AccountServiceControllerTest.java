package com.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.AccountServiceApplication;
import com.demo.dto.AccountDto;
import com.demo.dto.CurrencyDto;
import com.demo.dto.MoneyDto;
import com.demo.model.AccountEntity;
import com.demo.model.CurrencyEntity;
import com.demo.model.MoneyEntity;
import com.demo.repository.AccountRepository;
import com.demo.util.JsonUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = AccountServiceApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
public class AccountServiceControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private AccountRepository repository;

	private static final String ACC_NAME = "savings3";
	private static final String ACC_CURRENCY = "EUR";
	private static final BigDecimal ACC_MONEY = new BigDecimal("120.00");

	@After
	public void resetDb() {
		repository.deleteAll();
	}

	@Test
	public void whenValidInput_thenAccountIsCreated() throws IOException, Exception {
		AccountDto accDto = createTestAccountDto(ACC_NAME, ACC_CURRENCY, ACC_MONEY);
		mvc.perform(
				patch("/accountService/createAccount").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(accDto)));

		AccountEntity acc = repository.findByName(ACC_NAME).get();
		assertNotNull(acc);
		assertThat(acc).extracting(AccountEntity::getName).isEqualTo(ACC_NAME);
		assertEquals(ACC_CURRENCY, acc.getCurrency().getCurrencyCode());
		assertEquals(ACC_MONEY, acc.getMoney().getBalance());
	}

	@Test
	public void givenAccount_whenGetAccount_thenAccountIsRetrieveAndStatusIs200() throws Exception {
		createTestAccountentity();

		mvc.perform(get("/accountService/account/" + ACC_NAME).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name", is(ACC_NAME)))
				.andExpect(jsonPath("$.currency.currencyCode", is(ACC_CURRENCY)));
	}

	@Test
	public void whenInputIsInvalid_thenReturnsStatus400() throws Exception {
		AccountDto accDto = createTestAccountDto("", ACC_CURRENCY, ACC_MONEY);

		mvc.perform(
				patch("/accountService/createAccount").contentType("application/json").content(JsonUtil.toJson(accDto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void whenPathVariableIsInvalid_thenReturnsStatus500() throws Exception {

		mvc.perform(get("/accountService/account/sav")).andExpect(status().isInternalServerError());
	}

	private AccountDto createTestAccountDto(String name, String currency, BigDecimal balance) {
		CurrencyDto euro = new CurrencyDto(currency);
		MoneyDto money = new MoneyDto(balance);

		AccountDto accDto = new AccountDto();
		accDto.setName(name);
		accDto.setCurrency(euro);
		accDto.setMoney(money);
		accDto.setTreasury(true);

		return accDto;
	}

	private AccountEntity createTestAccountentity() {
		CurrencyEntity euro = new CurrencyEntity(ACC_CURRENCY);
		AccountEntity acc = new AccountEntity(ACC_NAME, euro, false);
		MoneyEntity balance = new MoneyEntity(ACC_MONEY, acc);

		euro.setAccounts(Arrays.asList(acc));
		acc.setMoney(balance);
		repository.save(acc);

		return acc;
	}

}
