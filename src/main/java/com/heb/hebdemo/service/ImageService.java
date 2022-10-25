package com.heb.hebdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.heb.hebdemo.model.Image;

public interface ImageService {
	
	ArrayList<String> getImaggaImageTags(String imageUrl);

	Image save(Image image);

	List<Image> findAll();

	List<Image> findAllByImageObjects(String tag);

	Optional<Image> findById(Long imageId);

}