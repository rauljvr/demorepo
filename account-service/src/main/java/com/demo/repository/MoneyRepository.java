package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.MoneyEntity;

@Repository
public interface MoneyRepository extends JpaRepository<MoneyEntity, Long> {

}
