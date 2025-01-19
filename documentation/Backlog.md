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
     **Status:** ✅ In Progress  
     **Description:** Establish a clear and maintainable architecture for the project, including controllers, services, and repositories.

### 3. **Configuration**
   - **Task: Security Configuration**  
     **Status:** ✅ Done 
     **Description:** Implement Spring Security for role-based access control (RBAC) and secure API endpoints.

### 4. **Authentication**
   - **Task: Authentication Endpoints**  
     **Status:** ✅ Done  
     **Description:** Implement endpoints for user authentication and session management.

### 5. **Employee Management**
   - **Task: Employee Entity**  
     **Status:** ✅ Done  
     **Description:** Create the Employee entity to manage employee details such as full name, job title, and contact information.

   - **Task: Employee Service**  
     **Status:** ✅ Done  
     **Description:** Develop service logic for CRUD operations on employee records.

   - **Task: Employee Endpoints**  
     **Status:** ✅ Done  
     **Description:** Expose RESTful API endpoints for employee management with search and filtering capabilities.

### 6. **Testing**
   - **Task: Unit Tests**  
     **Status:** ✅ Done  
     **Description:** Write unit tests for all services and core components using JUnit and Mockito.


### 7. **UI Implementation**
  - **Task: Employee Management UI**  
    **Status:** ✅ Done  
    **Description:** Develop a Swing-based user interface for managing employee records, with MigLayout and GridBagLayout.

  - **Task: Authentication UI**  
    **Status:** ✅ Done  
    **Description:** Create a login interface with username/password fields and token-based authentication handling.

  - **Task: Logs UI**  
    **Status:** ✅ Done  
    **Description:** Develop a UI to display audit logs in a scrollable, read-only text pane.

  - **Task: Users Management UI**  
    **Status:** ✅ Done  
    **Description:** Implement a UI for managing users and their roles.

### 8. **DevOps**
   - **Task: Dockerization**  
     **Status:** ✅ Done  
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
