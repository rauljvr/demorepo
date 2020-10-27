package com.demo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {

	@NotBlank(message = "CurrencyCode field is mandatory")
	@Size(min = 3, max = 3, message = "CurrencyCode field must be 3 characters")
	private String currencyCode;
}
