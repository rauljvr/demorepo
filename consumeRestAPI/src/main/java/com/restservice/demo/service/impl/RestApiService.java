package com.restservice.demo.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.restservice.demo.APIEndpointConfig;
import com.restservice.demo.dto.AlbumDto;
import com.restservice.demo.dto.PhotoDto;
import com.restservice.demo.model.AlbumEntity;
import com.restservice.demo.model.UserEntity;
import com.restservice.demo.repository.AlbumRepository;
import com.restservice.demo.repository.UserRepository;
import com.restservice.demo.restclient.CustomRestClient;
import com.restservice.demo.service.IRestApiService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RestApiService implements IRestApiService {

	private APIEndpointConfig endpointConfig;
	private CustomRestClient restClient;
	private ObjectMapper objectMapper;
	private XmlMapper xmlMapper;

	private UserRepository userRepository;
	private AlbumRepository albumRepository;

	@Value("${placeholder.output.albumJsonFile}")
	private String albumJsonFile;

	@Value("${placeholder.output.albumXmlFile}")
	private String albumXmlFile;

	private static final String ALBUM_ID_PARAM = "albumId";
	private static final String PHOTO_ID_PARAM = "photoId";

	@Autowired
	public RestApiService(APIEndpointConfig endpointConfig, CustomRestClient restClient, ObjectMapper objectMapper,
			XmlMapper xmlMapper, UserRepository userRepository, AlbumRepository albumRepository) {
		this.endpointConfig = endpointConfig;
		this.restClient = restClient;
		this.objectMapper = objectMapper;
		this.xmlMapper = xmlMapper;
		this.userRepository = userRepository;
		this.albumRepository = albumRepository;
	}

	// ALBUM

	@Override
	public List<AlbumDto> getAlbums() {
		log.info("Getting all Albums from {}", endpointConfig.getAlbumsPath());
		return Arrays.asList(restClient.get(endpointConfig.getAlbumsPath(), AlbumDto[].class));
	}

	@Override
	public AlbumDto getAlbum(Long albumId) {
		log.info("Getting Album from {}", endpointConfig.getAlbumPath());

		Map<String, Long> params = new HashMap<>();
		params.put(ALBUM_ID_PARAM, albumId);
		AlbumDto album = restClient.get(endpointConfig.getAlbumPath(), AlbumDto.class, params);

		this.storeAlbumData(album);
		return album;
	}

	@Override
	public AlbumDto updateAlbum(Long albumId, AlbumDto albumDto) {
		log.info("Updating Album from {}", endpointConfig.getAlbumPath());

		Map<String, Long> params = new HashMap<>();
		params.put(ALBUM_ID_PARAM, albumId);

		return restClient.put(endpointConfig.getAlbumPath(), albumDto, AlbumDto.class, params);
	}

	@Override
	public AlbumDto createAlbum(AlbumDto albumDto) {
		log.info("Creating Album from {}", endpointConfig.getAlbumsPath());
		return restClient.post(endpointConfig.getAlbumsPath(), albumDto, AlbumDto.class);
	}

	@Override
	public void deleteAlbum(Long albumId) {
		log.info("Deleting Album from {}", endpointConfig.getAlbumPath());
		Map<String, Long> params = new HashMap<>();
		params.put(ALBUM_ID_PARAM, albumId);

		restClient.delete(endpointConfig.getAlbumPath(), params);
	}

	// PHOTO

	@Override
	public PhotoDto getPhoto(Long photoId) {
		log.info("Getting Photo from {}", endpointConfig.getPhotoPath());

		Map<String, Long> params = new HashMap<>();
		params.put(PHOTO_ID_PARAM, photoId);

		return restClient.get(endpointConfig.getPhotoPath(), PhotoDto.class, params);
	}

	@Override
	public PhotoDto updatePhoto(Long photoId, @Valid PhotoDto photoDto) {
		log.info("Updating Photo from {}", endpointConfig.getPhotoPath());

		Map<String, Long> params = new HashMap<>();
		params.put(PHOTO_ID_PARAM, photoId);

		return restClient.put(endpointConfig.getPhotoPath(), photoDto, PhotoDto.class, params);
	}

	@Override
	public PhotoDto createPhoto(@Valid PhotoDto photoDto) {
		log.info("Creating Photo from {}", endpointConfig.getPhotosPath());
		return restClient.post(endpointConfig.getPhotosPath(), photoDto, PhotoDto.class);
	}

	private void storeAlbumData(AlbumDto album) {
		try {
			objectMapper.writeValue(new File(albumJsonFile), album);
			xmlMapper.writeValue(new File(albumXmlFile), album);
			persistAlbum(album);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	private void persistAlbum(AlbumDto album) {
		Optional<UserEntity> user = userRepository.findById(album.getUserId());
		if (user.isPresent()) {
			AlbumEntity albumE = AlbumEntity.builder().userId(user.get()).title(album.getTitle()).id(album.getId())
					.build();
			albumRepository.save(albumE);
		}
	}

}
