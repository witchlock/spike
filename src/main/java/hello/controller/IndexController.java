package hello.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.model.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiachen on 3/6/15.
 */
@RestController
public class IndexController {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger("IndexController");
    private final TypeReference<HashMap<String,Object>> mapperType
            = new TypeReference<HashMap<String,Object>>() {};

    @RequestMapping("/index")
    public String index(@RequestParam(value = "json", defaultValue = "") String json){
        try {
            Map<String, Object> objectMap = objectMapper.readValue(json, mapperType);
            Store.create(objectMap);
        } catch (IOException e) {
            logger.debug(e.getMessage());
            return "JSON Parsed Failed: " + json;
        }
        return "OK";
    }
}
