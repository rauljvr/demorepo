package com.demo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

	Optional<AccountEntity> findByName(String name);

	Page<AccountEntity> findAll(Pageable pageRequest);

}
