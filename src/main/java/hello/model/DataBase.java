package hello.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by xiachen on 3/8/15.
 */
public class DataBase {
    private String name;
    private long id = -1;

    private List<Map<String, Object>> data = Collections.synchronizedList(new ArrayList<>());

    public void create(Map<String, Object> objectMap) {
        objectMap.put("id", data.size());
        objectMap.put("created_at", System.currentTimeMillis());
        data.add(objectMap);
    }

    public void update(List<Map<String, Object>> updateData) {
        for (Map<String, Object> ud: updateData) {
            Object dataId = ud.get("id");
            if (dataId == null) {
                this.create(ud);
                continue;
            }
            data.set((Integer) dataId, ud);
        }
    }

    public Map<String, Object> pop() {
        return data.get(data.size() - 1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
