# 3_Layered_Application
Nuts Service Subscriber with PostgreSQL Persistence
 
## Overview
This project is a simple 3-layered Java application that connects to the NATS message bus, processes incoming messages, and stores them in a PostgreSQL database.

## 3-Layer Architecture
### 1. API Layer
   - Located in the API package.
   - Subscribes to the NATS updates subject.
   - Forwards received messages to the Business layer.
### 2. Business (Service) Layer
   - Located in the Business package.
   - Contains application logic: filtering, validation, and any business rules.
   - Delegates storage of valid messages to the Data layer.
### 3. Data Layer
   - Located in the Data package.
   - Responsible for communicating with the PostgreSQL database.
   - Provides methods to save messages to the messages table.

## Necessary components
### 1. A Java Build Tool (optional)
   - You can run this project from your IDE or command line.
   - If you’re using Gradle or Maven, ensure the relevant dependencies (`io.nats:jnats` and `org.postgresql:postgresql`) are in your build file.
### 2. Java
   - The project is tested with Java 23.0.1.
### 3. Docker & Docker Compose
   - Make sure you have Docker installed.
   - Make sure Docker Compose is installed.

## Running Instructions
### 1. PostgreSQL
   - Create empty database `hw1` in your system.
     - Remember the `user` and `password` of your database system (for example: `user` = postgres, `password` = ansergart1964)
### 2. Run docker-compose
   - Before running `docker-compose.yml` write in it your user and password in here:
     ```yaml
      POSTGRES_DB: hw1
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: ansergart1964
     ```
   - Run the following command to start PostgreSQL and NATS:
   ```bash
   docker-compose up --build 
   ```
   - The file `init.sql` must be executed at container startup (it creates the `messages` table in the `hw1` database).
     - If table `messages` is not created then run the following code in your database:
     ```bash
     CREATE TABLE IF NOT EXISTS messages (
         id SERIAL PRIMARY KEY,
         content TEXT NOT NULL,
         timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
     );
     ```
### 3. Run the Java app
Before running you need to set your user and password inside `MassageKeeper.java` in here:
     
``` java
     String url = "jdbc:postgresql://localhost:5432/hw1";
     String user = "postgres";
     String password = "ansergart1964";
```

Two options of launch:
#### A. IDE
   1. Open the project in your IDE (for example IntelliJ IDEA).
   2. Make sure the JARs (jnats.jar and postgresql.jar) are on your classpath
   3. Run Start.java.
   4. When the application starts, you will see:
     ```      
     Subscriber is running.
     ```

It will continue listening for messages from the updates subject on NATS.

#### B. Command line
   1. Compile all `.java` files (make sure to include the classpath for NATS and PostgreSQL drivers):

     ```
     cp ".;jnats-2.21.0.jar;postgresql-42.7.5.jar" -d out src\Start.java src\API\Subscriber.java src\Business\MassageProcessor.java src\Data\MassageKeeper.java
     ```
   2. Run the application:
   
     ```
     java -cp "out;jnats-2.21.0.jar;postgresql-42.7.5.jar" Start
     ```
   3.When the application starts, you will see:
   ```      
   Subscriber is running.
   ```
It will continue listening for messages from the updates subject on NATS.

### 4. Testing
- Send a test message to the `updates` subject:
     ```
     nats pub messages "Hello!" 
     ```
- Verification of sending data to PostgreSQL:
     ```sql
     SELECT * 
     FROM messages;
     ```