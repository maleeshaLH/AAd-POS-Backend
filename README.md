# ShopNest

**ShopNest** is a robust Inventory and Sales Management System designed to streamline operations for retail stores and small businesses.
This project was developed as part of an academic assignment to create a backend API for an existing front-end using Jakarta EE.
The application handles essential operations like managing customer data, product inventories, sales transactions, and order processing, ensuring efficient business operations.

## Project Overview

ShopNest is a backend system for an Inventory and Sales Management application developed using Java Servlets, JDBC, MySQL, and Apache Tomcat.
It provides RESTful APIs for managing products, inventory, sales, customers, and transactions in a retail environment.

### Key Features

* **User-Friendly Interface**: A well-designed front-end built with HTML, CSS, and JavaScript ensures a smooth user experience.
* **Customer Management**: Efficiently manage customer information, including adding, updating, and viewing customer data.
* **Inventory Management**: Handle product inventories, including the addition, deletion, and update of product information.
* **Sales Management**: Manage sales transactions with detailed views and processing options.
* **Layered Architecture**: Adheres to a layered architecture, separating business logic, data access, and presentation layers.
* **API Documentation**: Comprehensive API documentation is provided, detailing the available endpoints for interacting with the system.

## Project Structure

### Front-end

The front-end of ShopNest is crafted to deliver a seamless user experience. It includes:

* **Home View**: A welcoming interface to introduce users to the application.
* **Customer Management**: Tools for viewing, adding, updating, and managing customer details.
* **Product Management**: An interface for managing inventory, including product details, prices, and stock quantities.

### Back-end

The back-end, developed using Jakarta EE, manages server-side operations, data processing, and business logic. It is hosted on the Apache Tomcat server, ensuring reliable performance. Key components include:

* **API Endpoints**: Secure and efficient endpoints for customer, product, inventory, and sales management.
* **Database Interactions**: Managed through JNDI for efficient connection pooling and database access.
* **Logging**: Integrated logging mechanisms at appropriate levels to monitor and troubleshoot the application.

### Database

* **MySQL**: The application uses MySQL for managing persistent data, ensuring data integrity and security.
* **JNDI Configuration**: Used for efficient database connection pooling and management.

## Tech Stack

* **Front-end**: HTML, CSS, JavaScript (AJAX)
* **Back-end**: Jakarta EE, Apache Tomcat
* **Database**: MySQL, JNDI for database connection pooling
* **Build Tool**: Maven

## API Documentation

Explore the API documentation to understand how to interact with the system's backend:

- [Customer API Documentation](https://link-to-customer-api-docs)
- [Item API Documentation](https://link-to-item-api-docs)
- [Order API Documentation](https://link-to-order-api-docs)


## Best Practices

The project follows best coding practices, including:

* **Layered Architecture**: Ensuring clear separation of concerns.
* **Logging**: Implementing logging at various levels for effective monitoring.
* **Code Readability**: Adhering to standard coding conventions and practices.

## How to Run

1. Clone the repository:

```bash
git clone https://github.com/maleeshaLH/ShopNest.git
```

2. Set up the database using the provided SQL scripts.

3. Configure JNDI in the Tomcat server.

4. Build the project using Maven:

```bash
mvn clean install
```

5. Deploy the `.war` file to Apache Tomcat.

6. Access the application via:

```text
http://localhost:8080/ShopNest
```

## Contribution

Contributions are welcome! Please feel free to submit issues or pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
