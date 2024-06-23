package com.leet5.ecommerce.controller;

import com.leet5.ecommerce.service.factory.DatabaseBackupServiceFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backups")
public class BackupController {
    private final DatabaseBackupServiceFactory factory;

    public BackupController(DatabaseBackupServiceFactory factory) {
        this.factory = factory;
    }

    @GetMapping
    public ResponseEntity<String> manualBackup(@RequestHeader("api-version") int apiVersion) {
        final var service = factory.getService(apiVersion);
        service.performBackup();
        return ResponseEntity.ok("Backup complete.");
    }
}
