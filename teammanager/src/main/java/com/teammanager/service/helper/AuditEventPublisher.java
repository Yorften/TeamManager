package com.teammanager.service.helper;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.teammanager.event.EmployeeAuditEvent;
import com.teammanager.model.Employee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuditEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishEmployeeEvent(String action, Employee employee) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        EmployeeAuditEvent event = EmployeeAuditEvent.builder()
                .action(action)
                .employeeId(employee.getId())
                .employeeName(employee.getFullName())
                .modifiedBy(username)
                .timestamp(LocalDateTime.now())
                .build();

        eventPublisher.publishEvent(event);
    }

    public void publishEmployeeCreateEvent(Employee employee) {
        try {
            publishEmployeeEvent("CREATE", employee);
        } catch (Exception e) {
            log.error("Failed to serialize employee data", e);
        }
    }

    public void publishEmployeeUpdateEvent(Employee employee) {
        try {
            publishEmployeeEvent("UPDATE", employee);
        } catch (Exception e) {
            log.error("Failed to serialize update data", e);
        }
    }

    public void publishEmployeeDeleteEvent(Employee employee) {
        publishEmployeeEvent("DELETE", employee);
    }
}
