# antolink

## Table of Contents

- [Description](#description)
- [Usage](#usage)
    - [Locally](#locally)
    - [Deployment](#deployment)
- [What have I learned?](#what-have-i-learned)
- [Contributions](#contributions)
- [License](#license)

## Description

URL Shortener API built using [Spring Boot](https://spring.io/projects/spring-boot), [Kotlin](https://kotlinlang.org/), [Selenium](https://selenium.dev/), [MongoDB](https://www.mongodb.com/) and [Railway](https://railway.app/).

Click here for seeing the [Swagger API documentation](https://antolink-production.up.railway.app/api/swagger-ui.html).

## Usage

You can run it [locally](#locally) or use the [deployment](#deployment).

### Locally

To use the application locally, follow these steps:

1. Clone the repository and navigate to the project directory.

    ```bash
    git clone https://github.com/antonioalanxs/antolink.git
    cd backend
    ```

2. Install [JDK 17](https://www.oracle.com/es/java/technologies/downloads/#java17).

    ```bash
    sudo apt install openjdk-17-jdk
    ```

3. Install MongoDB.

    ```bash
    sudo apt install mongodb
    ```
   
4. Start the MongoDB service.

    ```bash
    sudo systemctl start mongodb
    ```

5. Create a new MongoDB database named `antolink`.

    ```bash
    mongo
    use antolink
    ```
   
6. Set Spring Boot environment variables `MONGODB_ROOT_USERNAME`, `MONGODB_ROOT_PASSWORD` and `AUTHORIZATION_CODE`.

7. Run the application.

    ```bash
    ./gradlew bootRun
    ```
   
    Now, the application is running on [http://localhost:8080/api](http://localhost:8080/api).

### Deployment

If you prefer to use the deployment, consume [https://antolink-production.up.railway.app/api](https://antolink-production.up.railway.app/api).

#### `POST /links`
Creates a new link based on the provided `LinkDTO`. **Requires an authorization code header**.

- **Response 201**: Link created successfully.
  - **Example**:
    ```json
    {
      "id": "66d1949cb4ea9d04d75f3f7a",
      "url": "https://www.youtube.com/",
      "shortCode": "YouTube",
      "usageCount": 0
    }
    ```
- **Response 409**: Conflict, the link already exists.
  - **Example**:
    ```json
    {
      "status": "CONFLICT",
      "detail": {
        "message": "{ url: \"https://x.com/\" } already exists"
      }
    }
    ```
- **Response 401**: Invalid authorization code header.
  - **Example**:
    ```json
    {
      "status": "UNAUTHORIZED",
      "detail": {
        "message": "Invalid Authorization code header"
      }
    }
    ```

#### `GET /links/{shortCode}`
Redirects to the URL associated with the provided short code.

- **Response 301**: Redirected successfully.
- **Response 404**: Link not found.

#### `GET /links`
Retrieves a list of all links in `LinkDTO` format.

- **Response 200**: Links retrieved successfully.
  - **Example**:
    ```json
    [
      {
        "url": "https://nova.elportaldelalumno.com/",
        "shortCode": "nova",
        "usageCount": 0
      },
      {
        "url": "https://www.stremio.com/",
        "shortCode": "stremio",
        "usageCount": 0
      }
    ]
    ```

For more detailed information, check the [Swagger API documentation](https://antolink-production.up.railway.app/api/swagger-ui.html).

## What have I learned?

- Review of Spring Boot architecture
- Managing environment variables in Spring Boot
- Spring Boot annotations and aspects
- Configuring Spring Boot integration tests
- CI pipeline setup in GitHub Actions for integration tests
- Link verification and duplicate redirection prevention using Selenium
- Spring Boot/Kotlin and MongoDB Application Deployment

## Contributions

If you would like to contribute to the project, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix (`git checkout -b feature/new-feature`).
3. Make your changes and commit them following the [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) format (`git commit -m 'feat: add new feature'` or `fix: correct a bug`).
4. Push your branch to the remote repository (`git push origin feature/new-feature`).
5. Create a Pull Request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.