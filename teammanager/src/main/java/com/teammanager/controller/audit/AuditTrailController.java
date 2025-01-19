package com.teammanager.controller.audit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing users actions history.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController
@RequestMapping("/api/logs")
@AllArgsConstructor
@Slf4j
@Tag(name = "Audit Logs", description = "APIs for retrieving employee action logs")
public class AuditTrailController {

    private static final String LOG_FILE_PATH = "audit_logs/employee_audit.log";

    /**
     * Retrieves the content of the employee audit log file.
     *
     * @return The content of the log file as a string, or an error message if the
     *         log file is not found or cannot be read.
     */
    @Operation(summary = "Retrieve Employee Audit Logs", description = "Fetches the content of the employee audit log file.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the log file."),
            @ApiResponse(responseCode = "500", description = "Error reading the log file or log file not found.")
    })
    @GetMapping("/employee-audit")
    public String getEmployeeAuditLog() {
        File logFile = new File(LOG_FILE_PATH);

        if (!logFile.exists()) {
            return "No audit log file found.";
        }

        StringBuilder logContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logContent.append(line).append("\n");
            }
        } catch (IOException e) {
            return "Error reading the log file: " + e.getMessage();
        }

        return logContent.toString();
    }
}
