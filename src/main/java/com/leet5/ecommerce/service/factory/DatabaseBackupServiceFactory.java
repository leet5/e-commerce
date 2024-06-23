package com.leet5.ecommerce.service.factory;

import com.leet5.ecommerce.service.DatabaseBackupService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class DatabaseBackupServiceFactory {
    private final ConcurrentMap<Integer, DatabaseBackupService> cache = new ConcurrentHashMap<>();

    public DatabaseBackupServiceFactory(List<DatabaseBackupService> services) {
        for (DatabaseBackupService service : services) {
            cache.put(service.getVersion(), service);
        }
    }

    public DatabaseBackupService getService(int version) {
        return cache.get(version);
    }
}
