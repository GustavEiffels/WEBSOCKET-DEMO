package com.openchat.gateway.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GlobalFilterTest extends AbstractGatewayFilterFactory<GlobalFilterTest.Setting> {

    public GlobalFilterTest(){
        super(Setting.class);
    }

    @Override
    public GatewayFilter apply(Setting config) {
        return (exchange, chain) ->{
            ServerHttpRequest  request   = exchange.getRequest();
            ServerHttpResponse response  = exchange.getResponse();

            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                System.out.println("This is Global");
            }));
        };
    }

    @Data
    public static class Setting{
        private String testString;
        private boolean pre;
        private boolean post;
    }
}
