package com.restservice.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restservice.demo.model.AlbumEntity;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {

}
