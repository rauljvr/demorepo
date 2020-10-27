package com.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Currency")
@Table(name = "currency")
@Getter
@Setter
@NoArgsConstructor
public class CurrencyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "code")
	private String currencyCode;

	@OneToMany(mappedBy = "currency", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AccountEntity> accounts = new ArrayList<>();

	public CurrencyEntity(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
