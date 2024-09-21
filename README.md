# APMC Management System

## Overview
The **APMC Management System** is a Java-based web application designed to facilitate the selling of agricultural produce by farmers in the APMC Market. It also enables traders to participate in the bidding process for crops. The system simulates auctioning using Object-Oriented Programming (OOP) principles, design patterns, and modern web technologies.

## Features
- **Auction Simulation**: Farmers can list their produce in the market, and traders can bid on available crops in real-time. The winner is determined automatically after the auction timer ends.
- **Thymeleaf for Dynamic Web Pages**: Thymeleaf is used to create interactive and user-friendly web pages for farmers and traders, providing a seamless experience for listing produce and participating in auctions.
- **Notifications**: After the auction is completed, the system automatically sends notifications to traders, the winning farmer, and workers regarding the availability of work.
- **Data Management**: Integrated with MySQL for reliable and efficient data storage to keep track of auctions, bids, farmer listings, and user information.

## Tools & Technologies
- **Java**
- **Spring Boot**
- **Thymeleaf**
- **OOP Concepts**
- **MySQL**

## Design Patterns
This project employs several design patterns to ensure clean architecture, maintainability, and scalability:

1. **Facade Pattern**: 
   - Simplifies the interaction with complex subsystems like auction management and bidding logic.
   - Provides a unified interface for interacting with core services such as crop listing, bidding, and user management.

2. **Builder Pattern**:
   - Constructs complex objects, such as auctions and produce listings, step by step.
   - Ensures flexibility in object creation, allowing different configurations for crop listings and auction settings.

3. **Observer Pattern**:
   - Enables real-time updates in the auction system.
   - Notifies traders when new bids are placed or when winning bids are confirmed.
   - Farmers and workers receive notifications upon auction completion.

## System Architecture
The **APMC Management System** is built using a layered architecture with Spring Boot:
- **Presentation Layer**: Thymeleaf generates dynamic HTML pages for user interaction.
- **Service Layer**: Handles core business logic, including bidding and auction management, using design patterns for clarity and maintainability.
- **Data Access Layer**: MySQL is used to store and retrieve data related to farmers, traders, and auctioned produce.

## Installation & Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/apmc-management-system.git
   ```

2. **Navigate to the project directory**:
   ```bash
   cd apmc-management-system
   ```

3. **Install dependencies and build the project**: 
   Ensure you have Maven installed, then run:
   ```bash
   mvn clean install
   ```

4. **Setup MySQL Database**:
   Create a MySQL database and configure the database URL, username, and password in the `application.properties` file.

5. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

6. **Access the application**: 
   Open your browser and go to `http://localhost:8080` to access the APMC Management System.
