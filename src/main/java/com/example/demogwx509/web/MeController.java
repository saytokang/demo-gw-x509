package com.example.demogwx509.web;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class MeController {

	@RequestMapping("/me")
	public Mono<?> me() {
		// @formatter:off
		return ReactiveSecurityContextHolder
			.getContext()
			.map(SecurityContext::getAuthentication)
			.map(auth -> "hello " + auth.getName());
		// @formatter:on
	}

}
