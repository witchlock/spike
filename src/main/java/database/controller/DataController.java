package database.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import database.fetcher.RequestFetcher;
import database.model.DataBase;
import database.model.Store;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by xiachen on 3/8/15.
 */
@RestController
public class DataController {

    public static final String CAN_T_RECOGNIZE_THIS_DATA_SOURCE = "Can't Recognize this data source: ";
    public static final int ALL_DATA_IN_DATABASE = 3;
    public static final int SINGLE_ELEMENT = 4;
    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @RequestMapping("/data/*/*")
    public String data() throws JsonProcessingException {
        String urlPath = RequestFetcher.getCurrentRequest().getServletPath();
        return getSingleElement(urlPath);
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
        DataBase dataBase = Store.stores.get(dataBaseId);
        String dataId = data[3];
        try {
            Map<String, Object> objectMap = dataBase.getData().get(Integer.parseInt(dataId));
            return objectWriter.writeValueAsString(objectMap);
        } catch (Exception e) {
            return "Can't find Id=" + dataId + " in " + dataBaseName;
        }
    }

    @RequestMapping("/data/*")
    public String getAll() throws JsonProcessingException {
        String urlPath = RequestFetcher.getCurrentRequest().getServletPath();
        String[] data = urlPath.split("\\/");
        System.out.println();
        if (data.length != ALL_DATA_IN_DATABASE) {
            return CAN_T_RECOGNIZE_THIS_DATA_SOURCE + urlPath;
        }
        String dataBaseName = data[2];

        Integer dataBaseId = Store.tableName.get(dataBaseName);
        if (dataBaseId == null) {
            return CAN_T_RECOGNIZE_THIS_DATA_SOURCE + urlPath;
        }
        return objectWriter.writeValueAsString(Store.stores.get(dataBaseId).getData());
    }
}
