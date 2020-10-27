package com.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.AccountDto;
import com.demo.dto.TransferAccountDto;
import com.demo.exceptionshandler.ForbiddenTransferException;
import com.demo.exceptionshandler.ResourceNotFoundException;
import com.demo.model.AccountEntity;
import com.demo.service.IAccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Raúl Verastegui
 *
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/accountService")
public class AccountServiceController {

	private IAccountService accountService;
	private ModelMapper modelMapper;

	@Autowired
	public AccountServiceController(@Lazy IAccountService accountService, ModelMapper modelMapper) {
		this.accountService = accountService;
		this.modelMapper = modelMapper;
	}

	/**
	 * Get the user account of the client
	 * 
	 * @param accountName
	 * @return a DTOs representation of the account
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/account/{accountName}")
	public ResponseEntity<AccountDto> getAccount(
			@PathVariable(value = "accountName") @Size(min = 4, max = 45) String accountName)
			throws ResourceNotFoundException {
		log.info("Getting the account: {}", accountName);
		return ResponseEntity.ok().body(convertToDto(accountService.getAccount(accountName)));
	}

	/**
	 * Get all account saved in system
	 * 
	 * @return a DTOs list representation of all accounts in system
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/accounts")
	public ResponseEntity<List<AccountDto>> getAccounts() throws ResourceNotFoundException {
		log.info("Getting all accounts");

		return ResponseEntity.ok().body(convertListToDtoList(accountService.getAccounts()));
	}

	/**
	 * It gets the list of all account saved in system
	 * 
	 * @param {@link Pageable} with the page information
	 * @return a pageable list of all account in system
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/accountsPageable")
	public ResponseEntity<Page<AccountDto>> getAccountsPageable(Pageable pageRequest) throws ResourceNotFoundException {
		log.info("Getting Pageable list for all accounts");

		return ResponseEntity.ok().body(convertPageToDtoList(accountService.getAccountsPagable(pageRequest)));
	}

	/**
	 * It creates an account in system
	 * 
	 * @param {@link AccountDto} with the account details
	 * @return the account created
	 * @throws ResourceNotFoundException
	 */
	@PatchMapping("/createAccount")
	public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto account) {
		log.info("Creating the account: {}", account.toString());

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(convertToDto(accountService.createAccount(convertToEntity(account))));
	}

	/**
	 * It makes a transfer operation between 2 accounts and updates both balances
	 * 
	 * @param {@link TransferAccountDto} with the transfer details
	 * @return the origin account with the balance updated
	 * @throws ResourceNotFoundException
	 * @throws ForbiddenTransferException
	 */
	@PutMapping("/transferMoneyAccount")
	public ResponseEntity<AccountDto> transferMoneyAccount(@Valid @RequestBody TransferAccountDto transferDto)
			throws ResourceNotFoundException, ForbiddenTransferException {
		log.info("Making the transfer from the account: {} to the account: {} for the amount of {}",
				transferDto.getFromAccount(), transferDto.getToAccount(), transferDto.getMoney());

		return ResponseEntity.status(HttpStatus.OK)
				.body(convertToDto(accountService.transferMoneyAccount(transferDto.getFromAccount(),
						transferDto.getToAccount(), transferDto.getMoney())));
	}

	private AccountDto convertToDto(AccountEntity accountEntity) {
		return modelMapper.map(accountEntity, AccountDto.class);
	}

	private List<AccountDto> convertListToDtoList(List<AccountEntity> accounts) {

		return accounts.stream().map(acc -> convertToDto(acc)).collect(Collectors.toList());
	}

	private Page<AccountDto> convertPageToDtoList(Page<AccountEntity> accounts) {

		List<AccountDto> accountList = accounts.getContent().stream().map(acc -> convertToDto(acc))
				.collect(Collectors.toList());

		return new PageImpl<>(accountList);
	}

	private AccountEntity convertToEntity(AccountDto accountDto) {
		return modelMapper.map(accountDto, AccountEntity.class);
	}

}
