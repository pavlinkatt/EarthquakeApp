# EarthquakeApp

A full-stack web application for fetching, storing, and visualizing recent earthquake events retrieved from the USGS public API.

---

## Tech Stack

**Backend**
- Java 17
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven

**Frontend**
- React
- Bootstrap
- Leaflet.js

---

## Architecture Overview

- **Controller layer** – handles HTTP requests
- **Service layer** – business logic
- **Repository layer** – database access
- **External API integration** – fetches earthquake data from USGS

---

## Project Setup Instructions

### Prerequisites
- Java 17+
- Maven
- Node.js v20+
- npm
- PostgreSQL

### Clone the repository
```bash
git clone https://github.com/pavlinkatt/EarthquakeApp.git
```

---

## Database Configuration

1. Install PostgreSQL
2. Create the database:
```sql
CREATE DATABASE EarthquakeAppDB;
```
3. Create `application-local.properties` in `EarthquakeApp/src/main/resources/`:
```properties
DB_PASSWORD=your_postgres_password
```
4. The table will be auto-created by Hibernate on first run

---

## How to Run Backend and Frontend

### Backend
1. Navigate to the backend folder:
```bash
cd EarthquakeApp/EarthquakeApp
```
2. Run the application:
```bash
./mvnw spring-boot:run
```
Backend runs on `http://localhost:8080`

### Frontend
1. Navigate to the frontend folder:
```bash
cd EarthquakeApp/frontend
```
2. Install dependencies:
```bash
npm install
```
3. Start the development server:
```bash
npm start
```
Frontend runs on `http://localhost:3000`

---

## API Endpoints

### Fetch and store earthquakes
```
POST /api/earthquakes/fetch
```

### Get all earthquakes
```
GET /api/earthquakes
```

### Filter earthquakes by time
```
GET /api/earthquakes/filter?after=2026-04-16T00:00:00Z
```

### Delete earthquake by ID (optional)
```
DELETE /api/earthquakes/{id}
```

---

## Error Handling

The application handles:
- API unavailability — throws `UsgsApiException`
- Null values and missing fields — null checks in service layer
- Record not found — throws `EarthquakeNotFoundException`
- Unexpected errors — caught by `GlobalExceptionHandler`

All errors are returned as structured JSON responses.

---

## Testing

Basic integration tests are implemented for the service layer using Spring Boot Test.

To run the tests:
```bash
cd EarthquakeApp/EarthquakeApp
./mvnw test
```

---

## Assumptions

- Only earthquakes with magnitude greater than 2.0 are stored
- Existing records are deleted before each new fetch to avoid duplicates
- Time filtering uses ISO 8601 format (e.g. `2026-04-16T00:00:00Z`)
- USGS API endpoint used: `https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson`
- The external API is dynamic and data changes frequently — each fetch replaces existing data

---

## Optional Improvements Implemented

- Interactive map visualization using Leaflet.js with color-coded markers based on magnitude
- Delete specific earthquake record endpoint
- Color-coded magnitude badges in table (green/orange/red)
- Global exception handler returning clean JSON error responses
