# How to Use This Project

## Run
1. Start the MySQL server and create a database named `finance_db`.
2. Update `src/main/resources/application.properties` with your MySQL `username` and `password`.
3. Start the app with `./mvnw spring-boot:run` (or via your IDE).

## Authentication (Basic Auth)
This project uses HTTP Basic Auth.

Default users are inserted at startup (via `DataInitializer`):
- `admin / admin123` (role `ADMIN`)
- `analyst / analyst123` (role `ANALYST`)
- `viewer / viewer123` (role `VIEWER`)

## API Endpoints (Roles)

### User Management (Admin only)
- `POST   /api/users`
- `GET    /api/users`
- `GET    /api/users/{id}`
- `PUT    /api/users/{id}`
- `DELETE /api/users/{id}`

### Financial Records
Analyst/Admin can read records; only Admin can create/update/delete.

- `POST   /api/records` (Admin only)
- `GET    /api/records` (Analyst/Admin)
  - Optional filters: `type` (INCOME|EXPENSE), `category`, `startDate`, `endDate`
  - Pagination: standard Spring Data params `page`, `size`
- `GET    /api/records/{id}` (Analyst/Admin)
- `PUT    /api/records/{id}` (Admin only)
- `DELETE /api/records/{id}` (Admin only)

### Dashboard Aggregations (Viewer/Analyst/Admin)
- `GET /api/dashboard/summary`
- `GET /api/dashboard/category-totals`
- `GET /api/dashboard/recent-activity?limit=10`
- `GET /api/dashboard/trends?granularity=MONTHLY&startDate=YYYY-MM-DD&endDate=YYYY-MM-DD`
  - `granularity` can be `MONTHLY` or `WEEKLY`

## Error Format
Validation and API errors return a consistent JSON body:
`{ timestamp, status, error, message, path, details }`
