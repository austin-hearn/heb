package com.heb.hebdemo.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.heb.hebdemo.dto.ImageDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class Image implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Image URL cannot be null.")
	@Column(name = "image_url")
	private String imageUrl;
	
	@Column(name = "object_label")
	private String objectLabel;
	
	@Column(name = "objects_detected")
	private String objectsDetected;
	
	public Image(Long id) {
		this.id = id;
	}

	public ImageDTO convertEntityToDTO() {
		return new ModelMapper().map(this, ImageDTO.class);
	}
}