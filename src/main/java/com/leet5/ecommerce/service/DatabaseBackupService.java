package com.leet5.ecommerce.service;

public interface DatabaseBackupService {
    void performBackup();

    int getVersion();
}
