package com.demo.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.demo.dto.AccountDto;
import com.demo.model.AccountEntity;

@Component
public class MapConverter {

	private ModelMapper modelMapper;
	
	@Autowired
	public MapConverter(@Lazy ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public AccountDto convertToDto(AccountEntity accountEntity) {
		return modelMapper.map(accountEntity, AccountDto.class);
	}

	public List<AccountDto> convertListToDtoList(List<AccountEntity> accounts) {

		return accounts.stream().map(acc -> convertToDto(acc)).collect(Collectors.toList());
	}

	public Page<AccountDto> convertPageToDtoList(Page<AccountEntity> accounts) {

		List<AccountDto> accountList = accounts.getContent().stream().map(acc -> convertToDto(acc))
				.collect(Collectors.toList());

		return new PageImpl<>(accountList);
	}

	public AccountEntity convertToEntity(AccountDto accountDto) {
		return modelMapper.map(accountDto, AccountEntity.class);
	}

}