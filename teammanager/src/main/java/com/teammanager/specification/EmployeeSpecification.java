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
            if (criteria.getJobTitle() != null) {
                predicates.add(builder.like(root.get("jobTitle"), "%" + criteria.getJobTitle() + "%"));
            }
            if (criteria.getDepartment() != null) {
                predicates.add(builder.like(root.get("department"), "%" + criteria.getDepartment() + "%"));
            }
            if (criteria.getEmploymentStatus() != null) {
                predicates.add(builder.equal(root.get("employmentStatus"), criteria.getEmploymentStatus()));
            }
            if (criteria.getHireDateFrom() != null && criteria.getHireDateTo() != null) {
                predicates.add(
                        builder.between(root.get("hireDate"), criteria.getHireDateFrom(), criteria.getHireDateTo()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
