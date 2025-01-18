package com.teammanager.controller.audit;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing users actions history.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController
@RequestMapping("/api/audit")
@AllArgsConstructor
@Slf4j
public class AuditTrailController {
    
}
