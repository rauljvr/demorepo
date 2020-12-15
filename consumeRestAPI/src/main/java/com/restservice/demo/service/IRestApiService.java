package com.restservice.demo.service;

import java.util.List;

import javax.validation.Valid;

import com.restservice.demo.dto.AlbumDto;
import com.restservice.demo.dto.PhotoDto;

public interface IRestApiService {

	// Album
	public List<AlbumDto> getAlbums();

	public AlbumDto getAlbum(Long albumId);

	public AlbumDto updateAlbum(Long albumId, AlbumDto albumDto);

	public AlbumDto createAlbum(AlbumDto albumDto);

	public void deleteAlbum(Long albumId);

	// Photo
	public PhotoDto getPhoto(Long photoId);

	public PhotoDto updatePhoto(Long photoId, @Valid PhotoDto photoDto);

	public PhotoDto createPhoto(@Valid PhotoDto photoDto);

}
