package com.example.springbootreactive.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class FluxAndMonoController {
	
	@GetMapping("/fluxdata")
	public Flux<String> getFluxData(){
		return Flux.just("Spring","Controller","Example")
				.delayElements(Duration.ofSeconds(1))
				.log();
	}
	@GetMapping(value="/fluxstreamdata",produces= MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Integer> getFluxStreamData(){
		return Flux.just(1,2,3,4)
				.delayElements(Duration.ofSeconds(1))
				.log();
	}

}
