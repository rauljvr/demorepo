package com.demo.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDto {

	@Positive(message = "balance field must be positive")
	@Min(value=1, message = "The minimum balance is €1")
	private BigDecimal balance;
}
