package com.example.demogwx509.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.preauth.x509.SubjectDnX509PrincipalExtractor;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class BeanConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		SubjectDnX509PrincipalExtractor principalExtractor = new SubjectDnX509PrincipalExtractor();
		principalExtractor.setSubjectDnRegex("CN=(.*?)(?:,|$)");
		ReactiveAuthenticationManager authenticationManager = authentication -> {
			log.info("** OU: {}", authentication.getName());
			authentication.setAuthenticated(true);
			return Mono.just(authentication);
		};

		// @formatter:off
		http
            .x509(x509 -> x509
                .principalExtractor(principalExtractor)
                .authenticationManager(authenticationManager))
			.authorizeExchange(exchanges -> exchanges.anyExchange().permitAll());
        // @formatter:on

		http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance());
		return http.build();
	}

}
