package com.restservice.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Company")
@Table(name = "company")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class CompanyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Builder.Default
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserEntity> users = new ArrayList<>();

	private String name;
	private String catchPhrase;
	private String bs;

	public CompanyEntity(Long id, List<UserEntity> users, String name, String catchPhrase, String bs) {
		super();
		this.id = id;
		this.users = users;
		this.name = name;
		this.catchPhrase = catchPhrase;
		this.bs = bs;
	}

}
