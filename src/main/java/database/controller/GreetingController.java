package database.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiachen on 3/6/15.
 */
@RestController
public class GreetingController {
    @RequestMapping("/")
    public String greeting() {
        return "Hello World~";
    }
}
