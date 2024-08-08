package com.openchat.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomTestFilter extends AbstractGatewayFilterFactory<CustomTestFilter.ConfigTest> {

    public CustomTestFilter(){
        super(ConfigTest.class);
    }

    @Override
    public GatewayFilter apply(ConfigTest config) {
        // Custom Pre Filter
        return (exchange, chain) -> {
            ServerHttpRequest  request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                System.out.println("test");
            }));
        };
    }

    public static class ConfigTest{

    }
}
