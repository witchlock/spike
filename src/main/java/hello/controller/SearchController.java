package hello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiachen on 3/6/15.
 */
@RestController
public class SearchController {

    @RequestMapping("/search")
    public String search(@RequestParam(value="key", defaultValue = "World") String key) {
        return key;
    }
}
