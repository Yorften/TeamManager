package com.teammanager.event.listener;

import com.teammanager.event.EmployeeAuditEvent;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeAuditEventListener {

    private static final String AUDIT_DIR = "audit_logs";
    private static final DateTimeFormatter LOG_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Async
    @EventListener
    public void handleEmployeeAuditEvent(EmployeeAuditEvent event) {
        try {
            // Create audit_logs directory if it doesn't exist
            File auditDir = new File(AUDIT_DIR);
            if (!auditDir.exists()) {
                auditDir.mkdirs();
            }

            String filename = String.format("%s/employee_audit.log",
                    AUDIT_DIR);

            String logEntry = String.format("[%s] %s - Employee ID: %d, Name: %s, Initiated By: %s \n",
                    event.getTimestamp().format(LOG_TIME_FORMAT),
                    event.getAction(),
                    event.getEmployeeId(),
                    event.getEmployeeName(),
                    event.getModifiedBy());

            // try-with-resources to close resources
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
                writer.write(logEntry);
            }

        } catch (Exception e) {
            log.error("Failed to write audit log", e);
        }
    }
}
