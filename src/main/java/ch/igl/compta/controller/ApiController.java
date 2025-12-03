package ch.igl.compta.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test/")
public class ApiController {

    @GetMapping("/")
    public String index() {
        return "Welcome to IGL compta API!";
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    // move to admnin
    @GetMapping("/users")
    public String users() {
        return "test";
    }
}
