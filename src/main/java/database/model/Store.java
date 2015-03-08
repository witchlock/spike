package database.model;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xiachen on 3/6/15.
 */
public class Store {
    public static List<DataBase> stores = Collections.synchronizedList(new ArrayList<>());
    public static Map<String, Integer> tableName = new ConcurrentHashMap<>();

    public boolean delete() {
        return false;
    }

    public boolean put() {
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

    public static DataBase pop() {
        return stores.get(stores.size() - 1);
    }

    public static void clear() {
        stores = Collections.synchronizedList(new ArrayList<>());
        tableName = new ConcurrentHashMap<>();
    }
}
