package database.controller;

import database.model.Store;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.wordnik.swagger.annotations.Api;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by xiachen on 3/8/15.
 */
@RestController
@Api(value="", description="restful for a database")
public class DataController {
    public static final String CAN_T_RECOGNIZE_THIS_DATA_SOURCE = "Can't Recognize this data source: ";
    public static final int ALL_DATA_IN_DATABASE = 3;
    public static final int SINGLE_ELEMENT = 4;
    private final Logger logger = LoggerFactory.getLogger("DataController");

    @RequestMapping(value = "/data/*/*", method = RequestMethod.GET)
    public String get(HttpServletRequest request) throws JsonProcessingException {
        String urlPath = request.getServletPath();
        return getSingleElement(urlPath);
    }

    @RequestMapping(value = "/data/*/*", method = RequestMethod.DELETE)
    public String delete(HttpServletRequest request) throws JsonProcessingException {
        String urlPath = request.getServletPath();
        return deleteSingleElement(urlPath);
    }

    @RequestMapping(value = "/data/*/*", method = RequestMethod.POST)
    public String put(@RequestParam(value = "data", defaultValue = "") String data, HttpServletRequest request) throws JsonProcessingException {
        String urlPath = request.getServletPath();
        return putSingleElement(urlPath, data);
    }

    @RequestMapping(value = "/data/*", method = RequestMethod.GET)
    public String getAll(HttpServletRequest request) throws JsonProcessingException {
        String urlPath = request.getServletPath();
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
    public String post(@RequestParam(value = "data", defaultValue = "") String data, HttpServletRequest request) {
        String urlPath = request.getServletPath();
        return postSingleElement(urlPath, data);
    }

    private String postSingleElement(String urlPath, String jsonData) {
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
            Store.post(dataBaseId, jsonData);
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

    private String deleteSingleElement(String urlPath) {
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
            Store.delete(dataBaseId, Integer.parseInt(dataId));
        } catch (Exception e) {
            return "DELETE Failed: " + e.getMessage();
        }
        return "OK";
    }

    private String putSingleElement(String urlPath, String jsonData) {
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
            Store.put(dataBaseId, Integer.parseInt(dataId), jsonData);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return "PUT Failed: " + e.getMessage();
        }
        return "OK";
    }
}
