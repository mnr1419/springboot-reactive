package com.example.springbootreactive.FluxAndMonoTestCases;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


public class FluxAndMonoTest {
	
	
	// Please go through the this youtube channel for more info about Spring Reactive 
	// https://www.youtube.com/watch?v=IK26KdGRl48
	
	@Test
	public void fluxTest(){
		
		// Comment line number 16 code see difference 
		// if error is not occrred then log will show oncomplete lifecycle method and will print completed message.
		// if error is occur then onerror message will call instead of onComplete method
		Flux<String> stringFlux = Flux.just("Sample","demo","Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Execption occurred")))
				.concatWith(Flux.just("After Exception occur"))
				.log();
		stringFlux.subscribe(System.out::println,(e)->System.err.println(e.getMessage()),
				()->System.out.println("Completed"));
		
	}
	
	@Test
	public void fluxTest_WithOut_Error(){
		
	
		Flux<String> stringFlux = Flux.just("Sample","demo","Reactive Spring").log();
		
		// Method1
		/* StepVerifier.create(stringFlux).expectNext("Sample")
		 .expectNext("demo")
		 .expectNext("Reactive Spring")
		 .verifyComplete();*/
		
		// Method 2
		// if we change order of messges which is mentioned in expectNext method then 
		// test case will be fail, we should maintain the order how data comes from database/any sources/ Publisher
		StepVerifier.create(stringFlux).expectNext("Sample","demo","Reactive Spring")
		 .verifyComplete();
		
		 }
	
	@Test
	public void fluxTest_With_Error_Success(){
		
		Flux<String> stringFlux = Flux.just("Sample","demo","Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Execption occurred")))
				.log();

		StepVerifier.create(stringFlux).expectNext("Sample","demo","Reactive Spring")
		 .verifyError();
		
	}
	@Test
	public void fluxTest_With_Error_Failure(){
		
		Flux<String> stringFlux = Flux.just("Sample","demo","Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Execption occurred")))
				.log();

		StepVerifier.create(stringFlux).expectNext("Sample","demo","Reactive Spring")
		.expectNext("Message").verifyError();
		
	}
	@Test
	public void fluxTest_With_Error_Message_Success(){
		
		Flux<String> stringFlux = Flux.just("Sample","demo","Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("Exception occurred")))
				.log();

		StepVerifier.create(stringFlux).expectNext("Sample","demo","Reactive Spring")
		 .expectErrorMessage("Exception occurred")
		 .verify();
		
	}
	
	@Test
	public void fluxTest_With_Message_Count(){
		
		Flux<String> stringFlux = Flux.just("Sample","demo","Reactive Spring")
				.log();

		StepVerifier.create(stringFlux).expectNextCount(3).verifyComplete();
		
	}
	
	@Test
	public void monoTest(){
		Mono<String> monoString = Mono.just("Spring").log();
		StepVerifier.create(monoString).expectNext("Spring").verifyComplete();
		
	}
	

	@Test
	public void monoTest_Error_Success(){
		
		StepVerifier.create(Mono.error(new RuntimeException("Exception Occurred")).log())
          .expectError(RuntimeException.class).verify();
		
	}

}
