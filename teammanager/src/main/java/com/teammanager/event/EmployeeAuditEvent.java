package com.teammanager.event;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeAuditEvent {
    private String action;
    private Long employeeId;
    private String employeeName;
    private String modifiedBy;
    private LocalDateTime timestamp;
}
