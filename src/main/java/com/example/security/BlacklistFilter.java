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

/**
 * Http filter that checks incoming requests for a blacklisted JWT token
 * if the token is blacklisted, it returns HTTP 401 unauthorized.
 */

@Singleton
public class BlacklistFilter implements HttpServerFilter {

    // Injects the TokenBlacklist service to check blacklisted tokens
    @Inject
    TokenBlacklist blacklistService;

    /**
     * Filters incoming HTTP requests
     *
     * @param request The HTTP request
     * @param chain   The filter chain to proceed with the request if not blocked
     * @return a publisher emitting the HTTP response
     */
    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request,
                                                      ServerFilterChain chain) {

        String auth = request.getHeaders().getAuthorization().orElse(null);

        //check if the authorization header exists and is a Bearer token
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);

            //check if the token is blacklisted
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
