package com.restservice.demo.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumDto {

	private Long id;

	@NotBlank(message = "title field is mandatory")
	@Size(min = 1, max = 45, message = "title field must be between 1 and 45 characters")
	private String title;

	@Min(value = 1, message = "userId must be greater than zero")
	@NotNull(message = "userId field is mandatory")
	private Long userId;

	public AlbumDto(Long id, String title, Long userId) {
		super();
		this.id = id;
		this.title = title;
		this.userId = userId;
	}

}
