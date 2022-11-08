package com.masai.service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class UrlValidationService {

	public boolean isValidURL(String url) {
		
	    try {
	        new URL(url).toURI();
	        return true;
	    } catch (MalformedURLException e) {
	        return false;
	    } catch (URISyntaxException e) {
	        return false;
	    }
	    
	}

}
