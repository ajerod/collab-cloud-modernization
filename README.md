# Collab Cloud Modernization

## Overview

Collab Cloud Modernization is a Java backend portfolio project that simulates the modernization of a legacy collaborative platform into a cloud-native microservices architecture.

The goal is to demonstrate backend engineering practices around Spring Boot, REST APIs, Kafka, PostgreSQL, Docker, Kubernetes and CI/CD.

## Business Context

The project represents a collaborative platform where users can create workspaces, manage documents and track activities.

The initial system is based on a legacy monolith. The target architecture progressively extracts business capabilities into independent services.

## Target Technical Stack

- Java 17
- Spring Boot 3
- REST APIs
- PostgreSQL
- Apache Kafka
- Docker
- Kubernetes
- GitLab CI/CD
- AWS-compatible object storage
- JUnit 5
- Testcontainers

## Planned Services

- `workspace-service`: manages workspaces and members.
- `document-service`: manages document metadata and file storage.
- `activity-service`: consumes events and builds an activity feed.
- `notification-service`: consumes events and simulates notifications.
- `legacy-collab-monolith`: represents the legacy application to migrate progressively.

## Internal Service Architecture

Each microservice follows a pragmatic Hexagonal Architecture:

- `domain`: pure business model and domain events
- `application`: use cases and ports
- `adapter/in`: REST controllers and input adapters
- `adapter/out`: persistence, messaging and external system adapters
- `config`: technical configuration

## Project Roadmap

1. Initialize repository and documentation.
2. Create the first Spring Boot service.
3. Add PostgreSQL persistence.
4. Add REST APIs.
5. Add Kafka producer and consumer.
6. Add Docker Compose.
7. Add Kubernetes deployment manifests.
8. Add CI/CD pipeline.
9. Add legacy migration simulation.
10. Add performance tests.