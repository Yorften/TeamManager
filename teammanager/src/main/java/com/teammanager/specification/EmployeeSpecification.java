package com.teammanager.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.teammanager.dto.employee.EmployeeCriteria;
import com.teammanager.model.Employee;

import jakarta.persistence.criteria.Predicate;

public class EmployeeSpecification {

    public static Specification<Employee> withFilters(EmployeeCriteria criteria) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getId() != null) {
                predicates.add(builder.equal(root.get("id"), criteria.getId()));
            }
            if (criteria.getFullName() != null) {
                predicates.add(builder.like(root.get("fullName"), "%" + criteria.getFullName() + "%"));
            }
            if (criteria.getDepartment() != null) {
                predicates.add(builder.like(root.get("department"), "%" + criteria.getDepartment() + "%"));
            }
            if (criteria.getJobTitle() != null) {
                predicates.add(builder.equal(root.get("jobTitle"), criteria.getJobTitle()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
