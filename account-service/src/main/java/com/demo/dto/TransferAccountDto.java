package com.demo.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferAccountDto {

	@NotBlank(message = "fromAccount field is mandatory")
	@Size(min = 1, max = 45, message = "fromAccount field must be between 1 and 45 characters")
	private String fromAccount;

	@NotBlank(message = "fromAccount field is mandatory")
	@Size(min = 1, max = 45, message = "toAccount field must be between 1 and 45 characters")
	private String toAccount;

	@NotNull(message = "money field is mandatory")
	@Positive
	private BigDecimal money;
}
