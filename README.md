# Virtual-Museum-Online-Platform-Backend
![Java](https://img.shields.io/badge/Java-17-orange.svg) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-green.svg) ![Elasticsearch](https://img.shields.io/badge/Search-Elasticsearch-005571.svg) ![MongoDB](https://img.shields.io/badge/NoSQL-MongoDB-green.svg) ![Docker](https://img.shields.io/badge/Deploy-Docker-2496ED.svg)

**V-Museum Backend** is the high-performance server application powering the Virtual Museum. It features a **Dual-Database Architecture** for handling massive social interaction data, utilizes **Elasticsearch** for fuzzy search, and implements an intelligent **Recommendation System** based on user behavior analysis.

## 🏗️ System Architecture

The system uses a microservices-ready layered architecture, deployed via Docker on Google Cloud Platform (GCP).

```mermaid
graph TD
    Client[Frontend Client] -->|REST API| LB[Load Balancer]
    LB --> API[Spring Boot API Gateway]
    
    subgraph "Core Services"
        API --> Auth[Authentication Service]
        API --> Exhibit[Exhibition Service]
        API --> Social[Social Service (Likes/Comments)]
        API --> Rec[Recommendation Engine]
    end

    subgraph "Data Storage (Dual-DB)"
        Auth --> MySQL[(MySQL - User/Meta)]
        Exhibit --> MySQL
        Social --> Mongo[(MongoDB - Interactions)]
        Rec --> ES[(Elasticsearch)]
    end

    subgraph "External & Search"
        Exhibit --> Luma[Luma AI API]
        Exhibit --> ES
        Exhibit --> GCS[Google Cloud Storage]
    end
```
## ✨ Key Features

### 1. 🧬 Luma AI Integration Pipeline
Automates the creation of 3D content through a seamless pipeline.
- **Process:** Video Upload (GCS) -> Async Task Trigger (Luma API) -> Status Polling -> 3D Model Ready.
- **Efficiency:** Utilizes asynchronous processing to handle large video file conversions without blocking the main server threads.

### 2. 🔍 Elastic Search Engine (Fuzzy Search)
Provides robust and tolerant search capabilities for exhibitions and artifacts.
- **Fuzzy Matching:** Implements `Fuzzy Query` to handle typos and partial inputs (e.g., searching "dinosaor" finds "Dinosaur").
- **Indexing:** Automatically syncs metadata from MySQL to Elasticsearch upon exhibition creation or update.

### 3. ❤️ Social Interaction & Dual-Database
Designed to handle high-concurrency social features using a polyglot persistence strategy.
- **MySQL (Structured):** Stores core transactional data: Users, Exhibition Metadata, Scenes.
- **MongoDB (High-Volume):** Stores social interactions and logs:
    - **Comments:** Nested document structure for exhibition threads.
    - **Likes/Bookmarks:** Rapid read/write operations for user engagement.
    - **Browsing History:** Logs user dwell time and visited scenes.

### 4. 🧠 Intelligent Recommendation Engine
Delivers dynamic content suggestions to drive user retention.
- **Data Source:** Aggregates data from User History (MongoDB) and Likes/Collections.
- **Algorithm:** Uses a hybrid approach (Content-Based + Collaborative Filtering) to score exhibitions.
- **Example:** If a user likes "Renaissance Art" and comments on "Da Vinci", the system recommends "Italian History Museums".

## 🛠️ Tech Stack

| Component | Technology | Description |
| :--- | :--- | :--- |
| **Language** | Java 17 | Core logic |
| **Framework** | Spring Boot 3.x | Web MVC & DI |
| **Search** | Elasticsearch 7.x | Full-text & Fuzzy search |
| **RDBMS** | MySQL 8.0 | Transactional data (ACID) |
| **NoSQL** | MongoDB 5.0 | Social data & Logs |
| **Storage** | Google Cloud Storage | Video & 3D Assets |
| **Security** | Spring Security + JWT | Authentication |
| **DevOps** | Docker Compose | Containerization |

## 🔌 API Documentation Highlights

### Search & Discovery
- `GET /api/search?q={keyword}`: Fuzzy search exhibitions via Elasticsearch.
- `GET /api/recommendations/feed`: Get dynamic "For You" list based on user profile.

### Social Interactions
- `POST /api/exhibitions/{id}/like`: Toggle "Like" status.
- `POST /api/exhibitions/{id}/bookmark`: Add to user collection.
- `POST /api/exhibitions/{id}/comments`: Post a new comment.
- `GET /api/exhibitions/{id}/comments`: Retrieve comment threads.

### Luma 3D Generation
- `POST /api/generate`: Upload video and start 3D conversion.

## 🚀 Getting Started

### Prerequisites
- JDK 17
- Docker & Docker Compose (Recommended)
