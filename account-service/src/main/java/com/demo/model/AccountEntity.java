package com.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Account")
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 40, unique = true)
	private String name;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "currency_id", nullable = false)
	private CurrencyEntity currency;

	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
	private MoneyEntity money;

	private boolean treasury;

	public AccountEntity(String name, CurrencyEntity currency, boolean treasury) {
		this.name = name;
		this.currency = currency;
		this.treasury = treasury;
	}

	public AccountEntity(String name, CurrencyEntity currency, MoneyEntity money, boolean treasury) {
		this.name = name;
		this.currency = currency;
		this.money = money;
		this.treasury = treasury;
	}

}
