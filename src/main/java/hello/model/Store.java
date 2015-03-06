package hello.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by xiachen on 3/6/15.
 */
public class Store {
    private final AtomicLong id = new AtomicLong();

    public boolean create() {
        return false;
    }

    public boolean delete() {
        return false;
    }

    public boolean put() {
        return false;
    }

    public boolean get() {
        return false;
    }
}
