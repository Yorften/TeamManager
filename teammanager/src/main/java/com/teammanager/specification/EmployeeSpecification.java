package com.teammanager.specification;

import org.springframework.data.jpa.domain.Specification;

import com.teammanager.model.Employee;

public class EmployeeSpecification {

    public static Specification<Employee> searchByFullName(String term) {
        return (root, query, criteriaBuilder) -> {
            String pattern = "%" + term.toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), pattern);
        };
    }

    public static Specification<Employee> searchByDepartment(String term) {
        return (root, query, criteriaBuilder) -> {
            String pattern = "%" + term.toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("department")), pattern);
        };
    }

    public static Specification<Employee> searchByJobTitle(String term) {
        return (root, query, criteriaBuilder) -> {
            String pattern = "%" + term.toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("jobTitle")), pattern);
        };
    }

    public static Specification<Employee> searchById(Long employeeId) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("id"), employeeId);
        };
    }
}
