package com.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.CurrencyEntity;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {

	Optional<CurrencyEntity> findByCurrencyCode(String code);

}
