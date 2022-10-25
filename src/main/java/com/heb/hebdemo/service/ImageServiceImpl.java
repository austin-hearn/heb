package com.heb.hebdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import com.heb.hebdemo.model.Image;
import com.heb.hebdemo.repository.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService {
	
    private final WebClient webClient;
    
    @Autowired
    ImageRepository repository;
    
    //TODO: Move to config file for safety
    private final String baseUrl = "https://api.imagga.com/v2/tags?image_url=";
    private final String imaggaApiKey = "acc_3697423ea74822f";
    private final String imaggaApiSecret = "b2a0baf383c67790e31413ea85c5988b";
    
    public ImageServiceImpl() {
        this.webClient = WebClient.builder()
        		.filter(ExchangeFilterFunctions
                        .basicAuthentication(imaggaApiKey, imaggaApiSecret))
                .build();
    }
	
	@Override
	public ArrayList<String> getImaggaImageTags(String imageUrl) {
		String response =  webClient.get().uri(baseUrl + imageUrl). retrieve().bodyToMono(String.class).block();
		
		//TODO: Move to a parser method
		JSONObject jsonObject = new JSONObject(response);
		JSONObject jsonResultObject = jsonObject.getJSONObject("result");
		JSONArray tagsResponseArray = (JSONArray) jsonResultObject.get("tags");
		ArrayList<String> tagList = new ArrayList<String>();
		for(int i=0; i<tagsResponseArray.length(); i++){			
			JSONObject currentObject = tagsResponseArray.getJSONObject(i);
			JSONObject nestedObject = currentObject.getJSONObject("tag");
			tagList.add(nestedObject.getString("en"));
	    }
		
		return tagList;
	}
	
	@Override
	public Image save(Image image) {
		return repository.save(image);
	}
	
	@Override
	public List<Image> findAll() {
		return repository.findAll();
	}
	
	@Override
	public List<Image> findAllByImageObjects(String object) {
		return repository.findAllByImageObjects(object);
	}

	@Override
	public Optional<Image> findById(Long imageId) {
		return repository.findById(imageId);
	}

}