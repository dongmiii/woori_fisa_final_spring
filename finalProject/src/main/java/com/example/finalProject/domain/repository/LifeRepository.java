package com.example.finalProject.domain.repository;

import com.example.finalProject.domain.entity.LifeEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LifeRepository extends JpaRepository<LifeEntity, Long> {


    @Query(value = "SELECT DISTINCT f.title, f.summary, f.url FROM LifeEntity f")
    List<Object[]> findlifeDistinct();

    @Query("SELECT DISTINCT f.title, f.summary, f.url FROM LifeEntity f")
	Page<Object[]> findPagedlifeDistinct(Pageable pageable);
}