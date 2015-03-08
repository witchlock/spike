package database.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import database.fetcher.RequestFetcher;
import database.model.DataBase;
import database.model.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by xiachen on 3/8/15.
 */
@RestController
public class DataController {
    public static final String CAN_T_RECOGNIZE_THIS_DATA_SOURCE = "Can't Recognize this data source: ";
    public static final int ALL_DATA_IN_DATABASE = 3;
    public static final int SINGLE_ELEMENT = 4;
    private final Logger logger = LoggerFactory.getLogger("DataController");

    @RequestMapping(value = "/data/*/*", method = RequestMethod.GET)
    public String get() throws JsonProcessingException {
        String urlPath = RequestFetcher.getCurrentRequest().getServletPath();
        return getSingleElement(urlPath);
    }

    @RequestMapping(value = "/data/*", method = RequestMethod.GET)
    public String getAll() throws JsonProcessingException {
        String urlPath = RequestFetcher.getCurrentRequest().getServletPath();
        String[] data = urlPath.split("\\/");
        if (data.length != ALL_DATA_IN_DATABASE) {
            return CAN_T_RECOGNIZE_THIS_DATA_SOURCE + urlPath;
        }
        String dataBaseName = data[2];

        Integer dataBaseId = Store.tableName.get(dataBaseName);
        if (dataBaseId == null) {
            return CAN_T_RECOGNIZE_THIS_DATA_SOURCE + urlPath;
        }

        try {
            return Store.get(dataBaseId);
        } catch (IOException e) {
            return "Get DataBase: " + dataBaseName + " Failed: " + e.getMessage();
        }
    }

    @RequestMapping(value = "/data/*/", method = RequestMethod.POST)
    public String put(@RequestParam(value = "data", defaultValue = "") String data){
        String urlPath = RequestFetcher.getCurrentRequest().getServletPath();
        return putSingleElement(urlPath, data);
    }

    private String putSingleElement(String urlPath, String jsonData){
        String[] data = urlPath.split("\\/");
        if (data.length != ALL_DATA_IN_DATABASE) {
            return CAN_T_RECOGNIZE_THIS_DATA_SOURCE + urlPath;
        }
        String dataBaseName = data[2];

        Integer dataBaseId = Store.tableName.get(dataBaseName);
        if (dataBaseId == null) {
            return CAN_T_RECOGNIZE_THIS_DATA_SOURCE + urlPath;
        }
        try {
            Store.put(dataBaseId, jsonData);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            return "PUT Failed: " + e.getMessage();
        }
        return "OK";
    }

    private String getSingleElement(String urlPath) throws JsonProcessingException {
        String[] data = urlPath.split("\\/");
        if (data.length != SINGLE_ELEMENT) {
            return CAN_T_RECOGNIZE_THIS_DATA_SOURCE + urlPath;
        }
        String dataBaseName = data[2];

        Integer dataBaseId = Store.tableName.get(dataBaseName);
        if (dataBaseId == null) {
            return CAN_T_RECOGNIZE_THIS_DATA_SOURCE + urlPath;
        }
        String dataId = data[3];
        try {
            return Store.get(dataBaseId, dataId);
        } catch (Exception e) {
            return "Can't find Id=" + dataId + " in " + dataBaseName;
        }
    }
}
