package hello.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by xiachen on 3/6/15.
 */
public class Store {
    private final static AtomicLong id = new AtomicLong();

    public static List<Map<String, Object>> stores = new ArrayList<>();

    public boolean delete() {
        return false;
    }

    public boolean put() {
        return false;
    }

    public boolean get() {
        return false;
    }
    public static void create(Map<String, Object> objectMap) {
        objectMap.put("id", id.getAndIncrement());
        objectMap.put("created_at", System.currentTimeMillis());
        stores.add(objectMap);
    }

    public static Map<String, Object> pop() {
        return stores.get(stores.size() - 1);
    }
}
