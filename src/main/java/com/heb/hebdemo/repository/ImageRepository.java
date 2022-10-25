package com.heb.hebdemo.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.heb.hebdemo.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {


	Optional<Image> findByImageUrl(String imageUrl);
	
	List<Image> findAll();

	@Query(value = "SELECT * FROM Images i WHERE i.objects_detected LIKE %?1%", nativeQuery = true)
	List<Image> findAllByImageObjects(String object);
}