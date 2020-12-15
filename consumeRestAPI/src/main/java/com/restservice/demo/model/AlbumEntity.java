package com.restservice.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Album")
@Table(name = "album")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class AlbumEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity userId;

	@Builder.Default
	@OneToMany(mappedBy = "album", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PhotoEntity> photos = new ArrayList<>();

	public AlbumEntity(Long id, String title, UserEntity userId, List<PhotoEntity> photos) {
		super();
		this.id = id;
		this.title = title;
		this.userId = userId;
		this.photos = photos;
	}

}
