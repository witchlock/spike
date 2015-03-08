package database.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import database.model.DataBase;
import database.model.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by xiachen on 3/6/15.
 */
@RestController
public class IndexController {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private final Logger logger = LoggerFactory.getLogger("IndexController");

    @RequestMapping("/create")
    public String create(@RequestParam(value = "json", defaultValue = "") String json){
        try {
            DataBase dataBase = objectMapper.readValue(json, DataBase.class);
            dataBase = Store.create(dataBase);
            return objectWriter.writeValueAsString(dataBase);
        } catch (IOException e) {
            logger.debug(e.getMessage());
            return "JSON Parsed Failed: " + json;
        }
    }
}
