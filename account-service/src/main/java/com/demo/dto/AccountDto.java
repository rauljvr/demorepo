package com.demo.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDto {

	private Long id;

	@NotBlank(message = "Name field is mandatory")
	@Size(min = 1, max = 45, message = "Name field must be between 1 and 45 characters")
	private String name;

	@Valid
	private CurrencyDto currency;

	@Valid
	private MoneyDto money;

	@NotNull(message = "Treasury field is mandatory")
	private boolean treasury;
}
