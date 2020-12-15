package com.restservice.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restservice.demo.dto.AlbumDto;
import com.restservice.demo.dto.PhotoDto;
import com.restservice.demo.service.IRestApiService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Raúl Verastegui
 *
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/restApiService")
public class RestApiController {

	private IRestApiService restApiService;

	@Autowired
	public RestApiController(@Lazy IRestApiService restApiService) {
		this.restApiService = restApiService;
	}
	
	/**
	 * It gets an Album resource
	 * 
	 * @param albumId
	 * @return a DTO representation of the Album requested
	 */
	@GetMapping(value = "/albums/{albumId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AlbumDto> getAlbum(@PathVariable(value = "albumId") Long albumId) {
		log.info("Getting Album Id: {}", albumId);
		return ResponseEntity.ok().body(restApiService.getAlbum(albumId));
	}

	/**
	 * It gets all Albums resources
	 * 
	 * @return a DTOs list representation of all Albums stored in jsonplaceholder
	 */
	@GetMapping(value = "/albums", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AlbumDto>> getAlbums() {
		log.info("Getting all Albums");
		return ResponseEntity.ok().body(restApiService.getAlbums());
	}
	
	/**
	 * It updates the Album resource given in the request
	 * 
	 * @param albumId
	 * @param {@link  AlbumDto} with the Album details
	 * @return a DTO representation of the Album resource updated
	 */
	@PutMapping(value = "/albums/{albumId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AlbumDto> updateAlbum(@PathVariable(value = "albumId") Long albumId, 
			@Valid @RequestBody AlbumDto albumDto) {
		log.info("Updating Album: {}", albumDto.toString());
		return ResponseEntity.ok().body(restApiService.updateAlbum(albumId, albumDto));
	}
	
	/**
	 * It creates an Album resource
	 * 
	 * @param {@link AlbumDto} with the Album details
	 * @return a DTO representation of the Album resource created
	 */
	@PostMapping(value = "/albums", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AlbumDto> createAlbum(@Valid @RequestBody AlbumDto albumDto) {
		log.info("Creating Album: {}", albumDto.toString());

		return ResponseEntity.status(HttpStatus.CREATED).body(restApiService.createAlbum(albumDto));
	}
	
	/**
	 * It deletes an Album resource
	 * 
	 * @param albumId
	 * @return Successful status without content
	 */
	@DeleteMapping(value = "/albums/{albumId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteAlbum(@PathVariable(value = "albumId") Long albumId) {
		log.info("Deleting Album Id: {}", albumId);
		restApiService.deleteAlbum(albumId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * It gets a Photo resource
	 * 
	 * @param photoId
	 * @return a DTO representation of the Photo resource requested
	 */
	@GetMapping(value = "/photos/{photoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PhotoDto> getPhoto(@PathVariable(value = "photoId") Long photoId) {
		log.info("Getting Photo Id: {}", photoId);
		return ResponseEntity.ok().body(restApiService.getPhoto(photoId));
	}
	
	/**
	 * It updates the Photo resource given in the request
	 * 
	 * @param photoId
	 * @param {@link  PhotoDto} with the Photo details
	 * @return a DTO representation of the Photo resource updated
	 */
	@PutMapping(value = "/photos/{photoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PhotoDto> updatePhoto(@PathVariable(value = "photoId") Long photoId, 
			@Valid @RequestBody PhotoDto photoDto) {
		log.info("Updating Photo: {}", photoDto.toString());
		return ResponseEntity.ok().body(restApiService.updatePhoto(photoId, photoDto));
	}
	
	/**
	 * It creates a Photo resource
	 * 
	 * @param {@link PhotoDto} with the Photo details
	 * @return a DTO representation of the Photo resource created
	 */
	@PostMapping(value = "/photos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PhotoDto> createPhoto(@Valid @RequestBody PhotoDto photoDto) {
		log.info("Creating Photo: {}", photoDto.toString());

		return ResponseEntity.status(HttpStatus.CREATED).body(restApiService.createPhoto(photoDto));
	}

}
