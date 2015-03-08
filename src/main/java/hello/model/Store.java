package hello.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by xiachen on 3/6/15.
 */
public class Store {
    private final static AtomicLong id = new AtomicLong();

    public static List<DataBase> stores = Collections.synchronizedList(new ArrayList<>());

    public boolean delete() {
        return false;
    }

    public boolean put() {
        return false;
    }

    public boolean get() {
        return false;
    }

    public static void update(DataBase dataBase) {
        int dataBaseId = (int) dataBase.getId();
        DataBase needUpdate = stores.get(dataBaseId);
        needUpdate.update(dataBase.getData());
        stores.set(dataBaseId, needUpdate);
    }

    public static void create(DataBase databBase) {
        if (databBase.getId() != -1) {
            update(databBase);
            return;
        }
        databBase.setId(stores.size());
        stores.add(databBase);
    }

    public static DataBase pop() {
        return stores.get(stores.size() - 1);
    }

    public static void clear() {
        stores = Collections.synchronizedList(new ArrayList<>());
    }
}
