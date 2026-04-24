# ⚙️ Taskemer Server

> **The robust, scalable backend foundation for the Taskemer productivity ecosystem.**

Taskemer Server is a secure RESTful API built with **Java** and **Spring Boot**. It acts as the central hub for the Taskemer Android application, handling user authentication, data persistence, and complex task lifecycles. 

The codebase is engineered with maintainability in mind, strictly adhering to a **3-Layer Architecture** (Controller, Service, Repository) to ensure clean separation of concerns.

## ✨ Key Features

* **Secure Authentication:** Stateless session management using **Spring Security** and **JWT (JSON Web Tokens)**.
* **Complex Task Lifecycles:** Comprehensive endpoints for creating, updating, and managing projects and tasks.
* **Flexible Attachments:** Supports hybrid attachment handling, allowing users to attach either physical files or external URLs to their tasks.
* **Relational Data Mapping:** Efficient database interactions and relationship mapping using **Spring Data JPA** and **MySQL**.
* **Clean Architecture:** Strict adherence to Controller-Service-Repository layers with dedicated DTOs (Data Transfer Objects) to protect database entities from external exposure.

## 🛠 Tech Stack

* **Language:** Java 26+
* **Framework:** Spring Boot 4.0
* **Database:** MySQL
* **ORM:** Spring Data JPA / Hibernate
* **Security:** Spring Security & JWT
* **Build Tool:** Maven
* **API Architecture:** REST

## 🏗 Architecture Overview

The application follows a strict **3-Layer Architecture**:

1. **Controller Layer (API):** Intercepts HTTP requests, validates incoming payloads, and returns formatted HTTP responses and DTOs.
2. **Service Layer (Business Logic):** Contains the core business rules. It processes data, applies authorization rules, and acts as the bridge between the controllers and the database.
3. **Repository Layer (Data Access):** Interfaces directly with the MySQL database using Spring Data JPA, executing queries and returning Entities.

## 🚀 Getting Started

### Prerequisites
* Java Development Kit (JDK) 17 or higher
* MySQL installed and running locally (or via Docker)
* Maven

### Local Setup

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/Pop714/Taskemer-Server.git](https://github.com/Pop714/Taskemer-Server.git)
   cd Taskemer-Server
