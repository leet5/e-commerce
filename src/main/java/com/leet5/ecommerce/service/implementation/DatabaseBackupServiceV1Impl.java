package com.leet5.ecommerce.service.implementation;

import com.leet5.ecommerce.service.DatabaseBackupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DatabaseBackupServiceV1Impl implements DatabaseBackupService {
    private static final Logger log = LoggerFactory.getLogger(DatabaseBackupServiceV1Impl.class);
    private static final int VERSION = 1;

    @Value("${backup.pg_dump.container_name}")
    private String containerName;

    @Value("${backup.pg_dump.user}")
    private String dbUser;

    @Value("${backup.pg_dump.password}")
    private String dbPassword;

    @Value("${backup.pg_dump.dbname}")
    private String dbName;

    @Value("${backup.pg_dump.dir}")
    private String backupDir;

    @Scheduled(cron = "${backup.schedule.cron}")
    public void performBackup() {
        final var formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        final var timestamp = LocalDateTime.now().format(formatter);
        final var backupFilePath = backupDir + "/backup_" + timestamp + ".sql";

        final var processBuilder = new ProcessBuilder();
        processBuilder.command(
                "docker", "exec", containerName,
                "pg_dump",
                "-U", dbUser,
                "-F", "p",
                "-b",
                "-v",
                "-f", backupFilePath,
                dbName
        );

        processBuilder.environment().put("PGPASSWORD", dbPassword);

        try {
            final var process = processBuilder.start();

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                log.info("Backup finished successfully: {}", backupFilePath);
            } else {
                log.error("Backup failed with exit code: {}", exitCode);
            }
        } catch (IOException | InterruptedException e) {
            log.error("Backup failed", e);
        }
    }

    public int getVersion() {
        return VERSION;
    }
}
