package com.demo.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Money")
@Table(name = "money")
@Getter
@Setter
@NoArgsConstructor
public class MoneyEntity {

	@Id
	@Column(name = "id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private AccountEntity account;

	private BigDecimal balance;

	public MoneyEntity(BigDecimal balance, AccountEntity account) {
		this.balance = balance;
		this.account = account;
	}

	public MoneyEntity(BigDecimal balance) {
		this.balance = balance;
	}
}
