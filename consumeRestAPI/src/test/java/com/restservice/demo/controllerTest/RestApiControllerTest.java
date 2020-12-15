package com.restservice.demo.controllerTest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.restservice.demo.controller.RestApiController;
import com.restservice.demo.dto.AlbumDto;
import com.restservice.demo.dto.PhotoDto;
import com.restservice.demo.service.IRestApiService;
import com.restservice.demo.util.JsonUtil;

@RunWith(SpringRunner.class)
public class RestApiControllerTest {

	RestApiController controller;

	MockMvc mockMvc;

	@Mock
	private IRestApiService mockRestApiService;

	private AlbumDto albumDto;
	private AlbumDto albumDto2;
	private PhotoDto photoDto;

	@Before
	public void setUp() throws Exception {
		controller = new RestApiController(mockRestApiService);
		mockMvc = standaloneSetup(controller).build();

		albumDto = AlbumDto.builder().id(1l).userId(1l).title("Album title").build();
		albumDto2 = AlbumDto.builder().id(2l).userId(2l).title("Another Album title").build();
		photoDto = PhotoDto.builder().id(1l).albumId(1l).title("Photo title").url("http://domain.com")
				.thumbnailUrl("thumbnailUrl").build();
	}

	@Test
	public void getAlbum_whenAnyAlbumIdIsGiven_thenOkHttpCodeAndAlbumAreReturned() throws Exception {
		// Given albumDto

		// When/Then
		when(mockRestApiService.getAlbum(any())).thenReturn(albumDto);
		mockMvc.perform(get("/restApiService/albums/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id", is(albumDto.getId().intValue())))
				.andExpect(jsonPath("$.userId", is(albumDto.getUserId().intValue())))
				.andExpect(jsonPath("$.title", is(albumDto.getTitle())));
	}

	@Test
	public void getAlbums_whenServiceIsCalled_thenOkHttpCodeAndAlbumsAreReturned() throws Exception {
		// Given albumDto, albumDto2

		// When/Then
		when(mockRestApiService.getAlbums()).thenReturn(Arrays.asList(albumDto, albumDto2));
		mockMvc.perform(get("/restApiService/albums")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$[0].id", is(albumDto.getId().intValue())))
				.andExpect(jsonPath("$[0].userId", is(albumDto.getUserId().intValue())))
				.andExpect(jsonPath("$[0].title", is(albumDto.getTitle())))
				.andExpect(jsonPath("$[1].id", is(albumDto2.getId().intValue())))
				.andExpect(jsonPath("$[1].userId", is(albumDto2.getUserId().intValue())))
				.andExpect(jsonPath("$[1].title", is(albumDto2.getTitle())));
	}

	@Test
	public void updateAlbum_whenServiceIsCalledForAnyInputBodyGiven_thenOkHttpCodeAndAlbumAreReturned()
			throws Exception {
		// Given albumDto

		// When/Then
		when(mockRestApiService.updateAlbum(any(), any())).thenReturn(albumDto);
		mockMvc.perform(put("/restApiService/albums/1").contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(albumDto))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id", is(albumDto.getId().intValue())))
				.andExpect(jsonPath("$.userId", is(albumDto.getUserId().intValue())))
				.andExpect(jsonPath("$.title", is(albumDto.getTitle())));
	}

	@Test
	public void createAlbum_whenServiceIsCalledForAnyInputBodyGiven_thenCreatedHttpCodeAndAlbumAreReturned()
			throws Exception {
		// Given albumDto

		// When/Then
		when(mockRestApiService.createAlbum(any())).thenReturn(albumDto);
		mockMvc.perform(post("/restApiService/albums").contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(albumDto))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id", is(albumDto.getId().intValue())))
				.andExpect(jsonPath("$.userId", is(albumDto.getUserId().intValue())))
				.andExpect(jsonPath("$.title", is(albumDto.getTitle())));
	}

	@Test
	public void deleteAlbum_whenServiceIsCalledForAnyAlbumId_thenNoContentHttpCodeStatusIsReturned()
			throws Exception {
		// When/Then
		doNothing().when(mockRestApiService).deleteAlbum(any());
		mockMvc.perform(delete("/restApiService/albums/1")).andExpect(status().is(204));
	}

	@Test
	public void getPhoto_whenAnyPhotoIdIsGiven_thenOkHttpCodeAndPhotoAreReturned() throws Exception {
		// Given photoDto

		// When/Then
		when(mockRestApiService.getPhoto(any())).thenReturn(photoDto);
		mockMvc.perform(get("/restApiService/photos/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id", is(photoDto.getId().intValue())))
				.andExpect(jsonPath("$.albumId", is(photoDto.getAlbumId().intValue())))
				.andExpect(jsonPath("$.title", is(photoDto.getTitle())))
				.andExpect(jsonPath("$.url", is(photoDto.getUrl())))
				.andExpect(jsonPath("$.thumbnailUrl", is(photoDto.getThumbnailUrl())));
	}

	@Test
	public void updatePhoto_whenServiceIsCalledForAnyInputBodyGiven_thenOkHttpCodeAndPhotoAreReturned()
			throws Exception {
		// Given photoDto

		// When/Then
		when(mockRestApiService.updatePhoto(any(), any())).thenReturn(photoDto);
		mockMvc.perform(put("/restApiService/photos/1").contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(photoDto))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id", is(photoDto.getId().intValue())))
				.andExpect(jsonPath("$.albumId", is(photoDto.getAlbumId().intValue())))
				.andExpect(jsonPath("$.title", is(photoDto.getTitle())))
				.andExpect(jsonPath("$.url", is(photoDto.getUrl())))
				.andExpect(jsonPath("$.thumbnailUrl", is(photoDto.getThumbnailUrl())));
	}

	@Test
	public void createPhoto_whenServiceIsCalledForAnyInputBodyGiven_thenCreatedHttpCodeAndPhotoAreReturned()
			throws Exception {
		// Given photoDto

		// When/Then
		when(mockRestApiService.createPhoto(any())).thenReturn(photoDto);
		mockMvc.perform(post("/restApiService/photos").contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(photoDto))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id", is(photoDto.getId().intValue())))
				.andExpect(jsonPath("$.albumId", is(photoDto.getAlbumId().intValue())))
				.andExpect(jsonPath("$.title", is(photoDto.getTitle())))
				.andExpect(jsonPath("$.url", is(photoDto.getUrl())))
				.andExpect(jsonPath("$.thumbnailUrl", is(photoDto.getThumbnailUrl())));
	}

}
