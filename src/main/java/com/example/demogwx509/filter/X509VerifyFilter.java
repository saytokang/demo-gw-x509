package com.example.demogwx509.filter;

import java.util.Optional;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class X509VerifyFilter implements GlobalFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("----call X509VerifyFilter ");

		// @formatter:off
        return ReactiveSecurityContextHolder.getContext()
            .flatMap(ctx -> {
                String value = Optional.ofNullable(ctx.getAuthentication()).map(Authentication::getName).orElse("");
                boolean hasValue = value.equals("client");
                log.info(" x509 value: {}", value);

                return hasValue ? Mono.just("yes") : Mono.empty();
            })
            .switchIfEmpty(Mono.error(new RuntimeException("unAuthozed.")))
            .then(chain.filter(exchange));
        // @formatter:on

	}

	@Override
	public int getOrder() {
		return -10;
	}

}
