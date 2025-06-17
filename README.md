QMoney - Personal Portfolio Manager
QMoney is a simplified portfolio management system developed using Java. This application allows users to fetch stock data using REST APIs, analyze historical prices, and make portfolio decisions accordingly. It is designed as part of a backend development module and demonstrates RESTful programming, Java 8 features, and test-driven development principles.

🚀 Features
Fetch real-time and historical stock data via Tiingo API

Calculate total returns on a portfolio of stocks

Sort portfolio returns based on various criteria

REST API integration using Spring Boot and HTTP clients

Test-driven development using JUnit

🛠️ Tech Stack
Java 8+

Maven for dependency management

JUnit 5 for testing

Spring Boot (if used)

REST APIs (Tiingo or similar)

📁 Project Structure
swift
Copy
Edit
QMoney/
├── src/
│   ├── main/
│   │   └── java/com/crio/warmup/stock/
│   └── test/
│       └── java/com/crio/warmup/stock/
├── .gitignore
├── pom.xml
└── README.md
🧪 How to Run the Project
Prerequisites
Java 8 or later

Maven 3.6+

Internet connection (for API calls)

Tiingo API Token (sign up here)

Setup Instructions
Clone the repository

bash
Copy
Edit
git clone https://github.com/p12priya/QMoney.git
cd QMoney
Add your Tiingo API token

Update your token in application.properties or pass it as an environment variable as needed.

Build the project

bash
Copy
Edit
mvn clean install
Run Tests

bash
Copy
Edit
mvn test
📈 Example Functionality
The project supports:

Portfolio data parsing from a file

Fetching historical stock price from Tiingo

Calculating annualized returns

Sorting by return value or symbol

📚 Learning Goals
Understand REST API consumption in Java

Gain familiarity with LocalDate, Streams, and Lambda expressions

Learn how to write modular, testable Java code

Practice reading CSV and JSON data

Introduction to unit and integration testing

🧑‍💻 Author
Pushpanjali Priya
🔗 GitHub

📜 License
This project is for educational purposes only. Feel free to fork and modify it.
