# Personal Finance Tracker

A comprehensive Spring Boot web application for managing personal finances, tracking expenses, setting budgets, and generating financial reports.

## Features

- **Transaction Management**: Track income and expenses with categories, dates, and descriptions
- **Recurring Transactions**: Set up automatic recurring transactions (daily, weekly, monthly, yearly)
- **Budget Tracking**: Create monthly budgets per category with visual progress indicators
- **Financial Reports**: Generate monthly spending reports with category breakdowns
- **Category Management**: Organize transactions with customizable categories and color coding
- **Dashboard**: View balance, monthly income/expenses, and recent transactions at a glance

## Technologies Used

- **Backend**: Spring Boot 3.2.0, Spring Data JPA
- **Frontend**: Thymeleaf, HTML5, CSS3
- **Database**: H2 (development), easily switchable to PostgreSQL
- **Build Tool**: Maven
- **Java Version**: 17

## Project Structure

```
src/main/java/com/financetracker/
├── FinanceTrackerApplication.java          # Main application
├── config/
│   └── DataInitializer.java               # Sample data loader
├── controller/
│   ├── HomeController.java
│   ├── TransactionController.java
│   ├── BudgetController.java
│   ├── CategoryController.java
│   └── ReportController.java
├── service/
│   ├── TransactionService.java
│   ├── BudgetService.java
│   ├── CategoryService.java
│   └── ReportService.java
├── repository/
│   ├── TransactionRepository.java
│   ├── BudgetRepository.java
│   └── CategoryRepository.java
├── model/
│   ├── Transaction.java
│   ├── Budget.java
│   ├── Category.java
│   ├── TransactionType.java (enum)
│   └── RecurringFrequency.java (enum)
├── dto/
│   ├── TransactionDTO.java
│   ├── BudgetDTO.java
│   └── ReportDTO.java
└── exception/
    ├── ResourceNotFoundException.java
    └── GlobalExceptionHandler.java

src/main/resources/
├── application.properties
└── templates/
    ├── layout.html
    ├── index.html
    ├── transactions/
    │   ├── list.html
    │   └── form.html
    ├── budgets/
    │   ├── list.html
    │   └── form.html
    ├── categories/
    │   ├── list.html
    │   └── form.html
    └── reports/
        └── monthly.html
```

## Setup Instructions

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Installation

1. **Clone or create the project directory**
   ```bash
   mkdir personal-finance-tracker
   cd personal-finance-tracker
   ```

2. **Create the project structure**
   ```bash
   mkdir -p src/main/java/com/financetracker/{config,controller,service,repository,model,dto,exception}
   mkdir -p src/main/resources/templates/{transactions,budgets,categories,reports}
   mkdir -p src/main/resources/static
   mkdir -p src/test/java/com/financetracker
   ```

3. **Copy all the Java files** into their respective directories (as shown in the project structure)

4. **Copy the template files** (HTML) into `src/main/resources/templates/`

5. **Copy `pom.xml`** to the project root

6. **Copy `application.properties`** to `src/main/resources/`

### Running the Application

1. **Build the project**
   ```bash
   mvn clean install
   ```

2. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

3. **Access the application**
   - Open your browser and go to: `http://localhost:8080`
   - H2 Console (for database inspection): `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:file:./data/financetracker`
     - Username: `sa`
     - Password: (leave blank)

## Usage Guide

### Getting Started

1. **Dashboard**: The home page shows your total balance, monthly income/expenses, and recent transactions

2. **Add Transactions**:
   - Click "Add Transaction" from the dashboard or Transactions page
   - Fill in amount, type (Income/Expense), category, date, and optional description
   - Check "recurring" for automatic transactions

3. **Set Budgets**:
   - Go to Budgets page
   - Create monthly spending limits for each expense category
   - Visual progress bars show how much of your budget is used

4. **Generate Reports**:
   - View Reports page for monthly spending analysis
   - See breakdown by category with percentages
   - Track income vs expenses over time

5. **Manage Categories**:
   - Add custom categories for better organization
   - Assign colors for visual identification
   - Separate income and expense categories

## Database

The application uses H2 database by default, which stores data in a file (`./data/financetracker.mv.db`). This file persists between runs.

### Switching to PostgreSQL

To use PostgreSQL in production:

1. Update `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/financetracker
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

2. Add PostgreSQL dependency to `pom.xml`:
   ```xml
   <dependency>
       <groupId>org.postgresql</groupId>
       <artifactId>postgresql</artifactId>
       <scope>runtime</scope>
   </dependency>
   ```

Feel free to fork this project and add your own features!
