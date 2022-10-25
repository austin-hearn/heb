package com.heb.hebdemo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.heb.hebdemo.service.ImageService;
import com.heb.hebdemo.ImageNotFoundException;
import com.heb.hebdemo.dto.ImageDTO;
import com.heb.hebdemo.dto.Response;
import com.heb.hebdemo.model.Image;



@RestController
@RequestMapping("/heb-demo/images")
public class ImageController {

	private ImageService imageService;
	
	@Autowired
	public ImageController(ImageService imageService) {
		this.imageService = imageService;
	}
	
	@PostMapping
	public ResponseEntity<Response<ImageDTO>> create(@Valid @RequestBody ImageDTO dto, BindingResult result) {
		
		Response<ImageDTO> response = new Response<>();
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.addErrorMsgToResponse(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		if ((null != dto.getObjectDetectionEnabled() && dto.getObjectDetectionEnabled() == true) && 
				(null == dto.getObjectLabel() || dto.getObjectLabel().isEmpty())) {
			ArrayList<String> tagList = imageService.getImaggaImageTags(dto.getImageUrl());
			dto.setObjectsDetected(String.join(", ", tagList));
			dto.setObjectLabel(tagList.get(0));
		}
		
		Image image = imageService.save(dto.convertDTOToEntity());
		ImageDTO imageDTO = image.convertEntityToDTO();

		response.setData(imageDTO);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<Response<List<ImageDTO>>> getAll() {
		
		Response<List<ImageDTO>> response = new Response<>();
		
		List<Image> imageList = imageService.findAll();
		List<ImageDTO> imageDTOList = new ArrayList<>();
		
		for (Image image : imageList) {
			ImageDTO imageDTO = image.convertEntityToDTO();
			imageDTOList.add(imageDTO);
		}

		response.setData(imageDTOList);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(params= {"objects"})
	public ResponseEntity<Response<List<ImageDTO>>> getByImageTag(@RequestParam("objects") String object) {
		
		Response<List<ImageDTO>> response = new Response<>();
		
		List<Image> imageList = imageService.findAllByImageObjects(object);
		List<ImageDTO> imageDTOList = new ArrayList<>();
		
		for (Image image : imageList) {
			ImageDTO imageDTO = image.convertEntityToDTO();
			imageDTOList.add(imageDTO);
		}

		response.setData(imageDTOList);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{imageId}")
	public ResponseEntity<Response<ImageDTO>> getByImageId(@PathVariable Long imageId) throws ImageNotFoundException {
		
		Response<ImageDTO> response = new Response<>();
		
		Optional<Image> image = imageService.findById(imageId);

		if (image.isEmpty()) {
			throw new ImageNotFoundException("There are no images with the id: " + imageId);
		}
		
		ImageDTO imageDTO = image.get().convertEntityToDTO();
		response.setData(imageDTO);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}