package com.heb.hebdemo.dto;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.heb.hebdemo.model.Image;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ImageDTO extends RepresentationModel<ImageDTO> {
	
	private Long id;

	@NotNull(message = "Image URL cannot be null.")
	private String imageUrl;
	
	private String objectLabel;
	
	private Boolean objectDetectionEnabled;
	
	private String objectsDetected;
	
	public Image convertDTOToEntity() {
		return new ModelMapper().map(this, Image.class);
	}
	
}