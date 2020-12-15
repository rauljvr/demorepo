package com.restservice.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Photo")
@Table(name = "photo")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class PhotoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String url;

	private String thumbnailUrl;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "album_id", nullable = false)
	private AlbumEntity album;

	public PhotoEntity(Long id, String title, String url, String thumbnailUrl, AlbumEntity album) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
		this.thumbnailUrl = thumbnailUrl;
		this.album = album;
	}

}
