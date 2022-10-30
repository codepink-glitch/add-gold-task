package services;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// Параметр - ключ сущности, по которому производим синхронизацию
public abstract class SynchronizedService<T> {

    private final ConcurrentMap<T, Object> syncMap = new ConcurrentHashMap<>();

    protected SynchronizedService() {
        super();
    }

    public Object getSyncObject(final T id) {
        syncMap.putIfAbsent(id, new Object());
        return syncMap.get(id);
    }
}
