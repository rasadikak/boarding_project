# Boarding House Management System (Backend)

A full-featured REST API for managing a boarding house / hostel, built with **Spring Boot**. It supports two roles — **Admin** and **Tenant** — with JWT-based authentication, role-based access control, and email notifications. A React frontend is planned as a future addition.

## Overview

This backend handles the day-to-day operations of running a boarding house: managing rooms, onboarding and tracking tenants, recording rent payments, handling maintenance complaints, and sending notices and reminders — all through a secure, role-protected API.



## Architecture

The project follows a **package-by-feature** structure, with each business feature (not each role) as its own module:

```
com.kaushani.demo
├── auth              # Users, roles, JWT, login, password reset
├── room               # Room entity, availability tracking
├── tenant             # Tenant profiles, linked to a User and a Room
├── payment            # Rent payments and status tracking
├── complaint          # Tenant complaints and resolution status
├── notifications      # Email-based notices and reminders
```

Role-based permissions are enforced per-endpoint using Spring Security's `@PreAuthorize`, rather than duplicating logic across separate admin/tenant modules - a room, tenant, or payment endpoint is shared code with role checks layered on top, not a separate copy per role.



## Tech Stack

- **Backend:** Java, Spring Boot, Spring Security, Spring Data JPA (Hibernate)
- **Database:** MySQL
- **Auth:** JWT (JSON Web Tokens)
- **Email:** Spring Mail 
- **Build tool:** Maven


## Getting Started

1. Clone the repository
2. Configure your database and mail settings in `src/main/resources/application.properties`
3. Run with Maven:
   ```
   ./mvnw spring-boot:run
   ```
4. The API will be available at `http://localhost:8081/api`