package com.example;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.Map;

@Controller
public class HomeController {
    //Anya for you to fill for home page
    // Logic is the controller handles HTTP requests and
    // sends JSON response to the React frontend
//    @Get("/")
//    public HttpResponse<Map<String, Object>> getHomeData() {
//        // Simulate home page data your React app might need
//        Map<String, Object> data = Map.of(
//                "welcomeMessage", "Welcome to PlayableGames!",
//                "subtitle", "Discover and manage your favorite games easily.",
//                "featuredGame", Map.of(
//                        "title", "Space Explorer",
//                        "description", "An intergalactic adventure where you explore new worlds!",
//                        "rating", 4.8
//                )
//        );
//
//        return HttpResponse.status(HttpStatus.OK).body(data);
//    }
}
