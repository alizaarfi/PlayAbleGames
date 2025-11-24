package com.example.security;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import jakarta.inject.Singleton;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;

@Singleton
public class BlacklistFilter implements HttpServerFilter {

    @Inject
    TokenBlacklist blacklistService;

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request,
                                                      ServerFilterChain chain) {

        String auth = request.getHeaders().getAuthorization().orElse(null);

        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);

            if (blacklistService.isBlacklisted(token)) {
                // Return Unauthorized without Mono
                return subscriber -> {
                    subscriber.onNext(HttpResponse.status(HttpStatus.UNAUTHORIZED));
                    subscriber.onComplete();
                };
            }
        }

        return chain.proceed(request);
    }
}
