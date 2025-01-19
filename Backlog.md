# TeamManager | Employee Records Management System - Sprint 1 Backlog

## Sprint Dates: 14 Jan – 21 Jan

### Sprint Goals:
- Set up the foundational components of the Employee Records Management System.
- Implement core functionality for employees, permissions, and authentication.
- Establish robust project architecture and testing strategies.
- Implement a simple GUI for the management system.

---

## Backlog Tasks:

### 1. **Project Setup**
   - **Task: Spring Boot Setup**  
     **Status:** ✅ Done  
     **Description:** Set up the Spring Boot application with the necessary dependencies and configurations.

   - **Task: Spring Profiles**  
     **Status:** ✅ Done  
     **Description:** Configure application profiles for `dev` and `prod` environments.

### 2. **Project Architecture**
   - **Task: Design Project Architecture**  
     **Status:** 🔄 In Progress  
     **Description:** Establish a clear and maintainable architecture for the project, including controllers, services, and repositories.

### 3. **Configuration**
   - **Task: Security Configuration**  
     **Status:** ⬜ To Do  
     **Description:** Implement Spring Security for role-based access control (RBAC) and secure API endpoints.

   - **Task: Permissions Service**  
     **Status:** ⬜ To Do  
     **Description:** Develop a service to manage user roles and permissions for HR, Managers, and Administrators.

### 4. **Authentication**
   - **Task: Authentication Endpoints**  
     **Status:** ⬜ To Do  
     **Description:** Implement endpoints for user authentication and session management.

### 5. **Employee Management**
   - **Task: Employee Entity**  
     **Status:** ⬜ To Do  
     **Description:** Create the Employee entity to manage employee details such as full name, job title, and contact information.

   - **Task: Employee Service**  
     **Status:** ⬜ To Do  
     **Description:** Develop service logic for CRUD operations on employee records.

   - **Task: Employee Endpoints**  
     **Status:** ⬜ To Do  
     **Description:** Expose RESTful API endpoints for employee management with search and filtering capabilities.

### 6. **Testing**
   - **Task: Unit Tests**  
     **Status:** ⬜ To Do  
     **Description:** Write unit tests for all services and core components using JUnit and Mockito.

   - **Task: Integration Tests**  
     **Status:** ⬜ To Do  
     **Description:** Develop integration tests to validate API endpoint functionality and ensure proper interaction between system components.

### 5. **UI Implementation**
    # To do #

### 7. **DevOps**
   - **Task: Dockerization**  
     **Status:** ⬜ To Do  
     **Description:** Create Docker images for the application to facilitate testing and deployment.


---

## Key Information:
- **API Documentation:** To be created using Swagger/OpenAPI.
- **Tech Stack:**
  - **Backend:** Java 17, Spring Boot
  - **Database:** Oracle SQL
  - **UI:** Swing with MigLayout and GridBagLayout
- **Testing Tools:** JUnit, Mockito, Postman
- **Deployment:** Docker


---

## Notes:
- Ensure role-based permissions are implemented thoroughly during the **Security Configuration** task.
- Focus on reusability and modular design while developing the **Employee Service** and **Permissions Service**.
- For **Dockerization**, include environment variables for configuration and set up a local testing image.

---
