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
public class PhotoDto {

	private Long id;

	@NotNull(message = "albumId field is mandatory")
	@Min(value = 1, message = "albumId must be greater than zero")
	private Long albumId;

	@NotBlank(message = "Title field is mandatory")
	@Size(min = 1, max = 45, message = "Title field must be between 1 and 45 characters")
	private String title;

	private String url;
	private String thumbnailUrl;

	public PhotoDto(Long id, Long albumId, String title, String url, String thumbnailUrl) {
		this.id = id;
		this.albumId = albumId;
		this.title = title;
		this.url = url;
		this.thumbnailUrl = thumbnailUrl;
	}

}
