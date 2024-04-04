# Table of Contents

[Overview](#overview)
[Usage](#usage)
[Requirements](#requirements)
[License](#license)
[Contribution](#contribution)
[Future plans](#future-plans)
[Authors](#authors)

# Overview

Welcome to the table reservation application, a modern solution designed to streamline the process of managing table reservations in restaurants. This application is built using a microservice architecture, leveraging technologies such as Netflix Eureka Server, Spring Boot, and MySQL database.
The primary objective of this application is to provide restaurant owners a system to handle table reservations efficiently. By breaking down the application into smaller, independent services, we ensure flexibility, maintainability, and resilience.

# Usage

Before installing the project, make sure you have Docker installed on your machine. 
Docker can be downloaded and installed from the official Docker website. Here are the links to download Docker for different operating systems:

[Docker Desktop for Windows](https://docs.docker.com/desktop/install/windows-install/)

[Docker Desktop for Mac](https://docs.docker.com/desktop/install/mac-install/)

[Docker Desktop for Linux](https://docs.docker.com/desktop/install/linux-install/)


After downloading and installing Docker, follow these steps to install and run the project:

Clone the repository to your local machine.

Navigate to the root directory of the project.

Finally, start the Docker containers using Docker Compose. From the root directory of the project, run:

    `docker-compose up`

This will build and start the Docker containers defined in the docker-compose.yml file.

Please note that the frontend is under development, so this repository doesn't include any frontend.

# Requirements

- Docker

# License

This project is licensed under the [MIT License](https://opensource.org/license/mit), allowing for flexible use and modification.

# Contribution

Contributions are welcome! Feel free to open issues, submit pull requests, or provide feedback. If you'd like to contribute to the project, please follow these guidelines:

- Fork the repository.
- Create a new branch for your feature: git checkout -b feature-name.
- Make your changes and commit: git commit -m 'Add some feature'.
- Push to your fork: git push origin feature-name.
- Open a pull request.

# Future plans

In the upcoming stages of development, the project aims to achieve the following:

1. **Code Testing:**
   - Thoroughly test the entire codebase to ensure its reliability and identify any potential issues or bugs.

2. **Finish backend:**
   - Complete the communication between the microservices using WebClient to ensure seamless interaction and data exchange.

3. **Implement frontend:**
    - Develop the frontend using React Native to enable the application to be deployed on both Android and iOS platforms.

4. **CI/CD pipelines:**
    - Implement CI/CD pipelines for all the microservices using Jenkins to automate the build, test, and deployment processes, ensuring continuous integration and delivery.

5. **Implement terraform:**
    - Create a Terraform file to provision and manage the AWS environment, including infrastructure and resources required for the application deployment.

# Authors

List of the contributors to the project:

<a href="https://github.com/SebestyenBenedek/table_reservation_backend/graphs/contributors">
 <img src="https://contrib.rocks/image?repo=SebestyenBenedek/table_reservation_backend" />
</a>