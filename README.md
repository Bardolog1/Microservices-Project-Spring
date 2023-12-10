# Spring Boot Microservices Project

![flow.gif](flow.gif)

## Overview

Este proyecto consiste en una aplicación basada en microservicios desarrollada con Spring Boot. La arquitectura se compone de tres microservicios: Products, Inventory y Orders, cada uno implementando Lombok. Cada microservicio tiene su propia base de datos PostgreSQL o MySQL, alojada en contenedores Docker.

## Comunicación Microservicio a Microservicio

La comunicación sincronizada entre el microservicio de órdenes y el microservicio de inventario se realiza mediante WebClient (WebFlux). Este enfoque permite consultar el inventario antes de crear una orden y está respaldado por Resilience4j mediante un "Circuit Breaker".

## Eventos y Kafka

Al crear una orden, se emite un evento en un topic de un broker, el cual se encuentra en un clúster Apache Kafka gestionado por Zookeeper. El componente Notification Service de Spring Boot se suscribe al "topic" en Kafka y permanece allí a la escucha de eventos emitidos por el microservicio de Orders.

## Orquestación y Acceso Externo

El sistema completo está orquestado mediante Netflix Eureka para el descubrimiento de servicios, con un API Gateway para el acceso externo.

## Seguridad

La autenticación se maneja mediante Spring Security con tokens JWT y Keycloak, este ultimo proporciona facilidad a la hora de querer crear usuarios y roles de usuario, proporcionando una capa segura para la aplicación y facil de gestionar.

## Monitoreo y Seguimiento

La monitorización de microservicios, el seguimiento de logs y trazas se facilita mediante Zipkin, Prometheus y Grafana, proporcionando métricas clave para mejorar el rendimiento del sistema.

## Uso de Lombok

Lombok se ha integrado para reducir la verbosidad del código, mejorar la legibilidad y facilitar el mantenimiento del código fuente.

## Prerequisitos

Debes tener instalados los siguientes elementos en tu entorno de desarrollo:

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Java Development Kit (JDK)](https://openjdk.java.net/install/)
- [Java Runtime Environment (JRE)](https://www.java.com/es/download/manual.jsp)
- [Maven](https://maven.apache.org/install.cgi)

## Clonación del Repositorio

```bash
  git clone https://github.com/bardolog1/Microservices-Project-Spring.git
  cd Microservices-Project-Spring
````
## Configuración de Bases de Datos

Inicia las bases de datos con Docker:

```bash
  docker-compose up -d db-products db-inventory db-orders db-keycloak
  docker-compose up -d keycloak zookeeper kafka zipkin prometheus grafana

```

## Iniciar todos los contenedores 

Inicia Keycloak, Zookeeper, Kafka, Zipkin, Prometheus & Grafana

```bash
  docker-compose up -d keycloak zookeeper kafka zipkin prometheus grafana
```
Espera a que Keycloak se inicie y luego configura  realms,  clients, usuarios y roles de usuario según sea necesario.
