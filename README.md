# EasySchedule

Monorepo con:

- `backend/`: Spring Boot + Gradle
- `frontend/`: Angular

## Versiones objetivo

- Java: 17.0.18
- Node: 20.19.0
- npm: 10.8.2
- Angular CLI: 20.3.19

## 1) Preparar entorno

### Node y npm

Verifica versiones instaladas:

```bash
node -v
npm -v
```

Versiones requeridas:

- Node: 20.19.0
- npm: 10.8.2

### Java 17

El backend usa toolchain Java 17. Verifica:

```bash
java -version
```

Si tienes varias versiones de Java, usa Java 17 para ejecutar Gradle/Spring Boot.

### PostgreSQL

Config por defecto en `backend/src/main/resources/application.properties`:

- DB: `EasySchedule`
- Usuario: `postgres`
- Password: `postgres`

NO sobrescribir por variables de entorno:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JPA_DDL_AUTO`
- `CORS_ALLOWED_ORIGINS`

Referencia: `backend/.env.example`

La estructura de base de datos se inicializa desde un unico archivo:

- `backend/src/main/resources/db/schema.sql`

Ese archivo contiene tablas, constraints e inserciones base de universidades.

Si ya tenias una base previa del modelo anterior, recrea la base para evitar inconsistencias de esquema:

```sql
DROP DATABASE IF EXISTS "EasySchedule";
CREATE DATABASE "EasySchedule";
```

Luego vuelve a levantar backend con `./gradlew bootRun` para que se aplique `schema.sql`.

## 2) Levantar backend

```bash
cd backend
./gradlew bootRun
```
En caso de que no funcione probar:
```bash
cd backend
chmod +x gradlew
./gradlew bootRun
```

Healthcheck: src/main/resources/http/test.http
aca encontraras el rest para probar endpoints

## 3) Levantar frontend

```bash
cd frontend
npm install
npm start
```

Frontend: `http://localhost:4200`

## 4) Verificaciones rapidas

```bash
cd frontend && npm run build

cd backend && ./gradlew test
```
