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

@Entity(name = "User")
@Table(name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column
	private String username;

	@Column
	private String email;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "company_id", nullable = false)
	private CompanyEntity company;

	@Builder.Default
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AlbumEntity> albums = new ArrayList<>();

	public UserEntity(Long id, String name, String username, String email, CompanyEntity company,
			List<AlbumEntity> albums) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.company = company;
		this.albums = albums;
	}

}
