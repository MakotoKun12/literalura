# Literalura

## Descripción
El proyecto "LIteralura" es una aplicación desarrollada en Java que facilita la gestión y consulta de libros utilizando tecnologías modernas de persistencia de datos y consumo de APIs externas. La aplicación emplea Hibernate y JPA para la persistencia de datos en una base de datos relacional, garantizando la eficiencia y flexibilidad en sus operaciones. Además, la aplicación se integra con la API de Gutendex para recuperar su catálogo de libros con información actualizada y detallada.

## Características Principales

### Persistencia de Datos con Hibernate y JPA:

Utilización de Hibernate como implementación de JPA para manejar la persistencia de datos.
Mapeo objeto-relacional (ORM) para interactuar con la base de datos de manera eficiente.
Gestión de entidades y relaciones entre ellas para reflejar la estructura de la base de datos.

### Consumo de la API de Gutendex:

Integración con la API de Gutendex para obtener información sobre una amplia variedad de libros.
Búsqueda y filtrado de libros basados en diferentes criterios (autor, año, idioma).
Actualización periódica del catálogo local con datos obtenidos de la API para mantener la información al día.

### Arquitectura y Tecnologías Utilizadas:

- Backend: Java con Hibernate y JPA.
- Base de Datos: PostgreSQL.
- API Externa: Gutendex.
- Frameworks Adicionales: Spring Boot.

## Beneficios

- ✅ Aprovechamiento de las capacidades de JPA y Hibernate para una gestión eficiente de la persistencia de datos.
- ✅ Enriquecimiento del catálogo de libros mediante la integración con una API externa confiable.
- ✅ Mantenimiento de un sistema de información de libros actualizado y fácil de gestionar.

## Instalacion

1. Clona el repositorio
    ``` git clone git@github.com:MakotoKun12/literalura.git ```
2. Navega al directorio del proyecto
  ``` cd literalura ```
3. Configura la base de datos en el archivo `aplication,properties`
4. Ejecuta la aplicación
