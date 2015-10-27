package config;

import org.springframework.context.annotation.Bean;

import model.map.Advertisement;

public class AdsConfig {
	
	@Bean
	public Advertisement advertisement(){
		return new Advertisement();
	}
}
