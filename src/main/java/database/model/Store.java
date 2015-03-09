package database.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xiachen on 3/6/15.
 */
public class Store {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private static final TypeReference<HashMap<String,Object>> MAPREF
            = new TypeReference<HashMap<String,Object>>() {};

    private static final TypeReference<List<HashMap<String,Object>>> LISTREF
            = new TypeReference<List<HashMap<String,Object>>>() {};

    public static List<DataBase> stores = Collections.synchronizedList(new ArrayList<>());
    public static Map<String, Integer> tableName = new ConcurrentHashMap<>();

    private final Logger logger = LoggerFactory.getLogger("Store");

    public boolean delete() {
        return false;
    }


    public boolean get() {
        return false;
    }

    public static DataBase update(DataBase dataBase) {
        Integer dataBaseId = tableName.get(dataBase.getName());
        DataBase needUpdate = stores.get(dataBaseId);
        needUpdate.update(dataBase.getData());
        stores.set(dataBaseId, needUpdate);
        return needUpdate;
    }

    public static DataBase create(DataBase dataBase) {
        String dataBaseName = dataBase.getName();
        if (tableName.get(dataBaseName) != null) {
            return update(dataBase);
        }

        Integer dataBaseId = stores.size();
        tableName.put(dataBaseName, dataBaseId);
        dataBase.setId(dataBaseId);
        dataBase.initiateData();
        stores.add(dataBase);
        return dataBase;
    }

    public static boolean post(Integer dataBaseId, String jsonObject) throws IOException {
        DataBase dataBase = Store.stores.get(dataBaseId);
        dataBase.create(objectMapper.readValue(jsonObject, MAPREF));
        return false;
    }

    public static String get(Integer dataBaseId, String dataId) throws IOException {
        DataBase dataBase = Store.stores.get(dataBaseId);
        Map<String, Object> objectMap = dataBase.getData().get(Integer.parseInt(dataId));
        return objectWriter.writeValueAsString(objectMap);
    }

    public static String get(Integer dataBaseId) throws IOException {
        return objectWriter.writeValueAsString(Store.stores.get(dataBaseId).getData());
    }

    public static DataBase pop() {
        if (stores.size() == 0) return null;
        return stores.get(stores.size() - 1);
    }

    public static void clear() {
        stores = Collections.synchronizedList(new ArrayList<>());
        tableName = new ConcurrentHashMap<>();
    }

    public static void delete(Integer dataBaseId, Integer dataId) {
        DataBase dataBase = Store.stores.get(dataBaseId);
        dataBase.delete(dataId);
    }

    public static void put(Integer dataBaseId, Integer dataId, String jsonData) throws IOException {
        DataBase dataBase = Store.stores.get(dataBaseId);
        dataBase.put(dataId, objectMapper.readValue(jsonData, MAPREF));
    }
    public static Map<String, Object> parseMap(String jsonData) throws IOException {
        return objectMapper.readValue(jsonData, MAPREF);
    }

    public static List<Map<String, Object>> parseList(String jsonData) throws IOException {
        return objectMapper.readValue(jsonData, LISTREF);
    }
}
