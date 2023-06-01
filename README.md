# The Demo of Read/Write Split with SpringBoot

## Setup Database Replication with Docker

### Startup the Database.

```shell
docker-compose up -d
```

### Setup Database Replication Role

- Access the psql client

```shell
psql -U postgres
```

- Create the user 'reading_user' and giving him permissions

```shell
CREATE USER reading_user WITH PASSWORD 'reading_pass';
GRANT CONNECT ON DATABASE my_database TO reading_user;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO reading_user;
GRANT SELECT ON ALL SEQUENCES IN SCHEMA public TO reading_user;
GRANT USAGE ON SCHEMA public TO reading_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT ON TABLES TO reading_user;
```

### Startup Database Proxy

```shell
docker-compose up -d -f docker-compose-proxy.yml
```

## Run the Spring Boot Application

### Native Datasource mode

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=direct
```

### Database Proxy mode

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=proxy
```

## Test with API

### Create a new user

```shell
curl --location 'http://localhost:8080/api/app-user' \
--header 'Content-Type: application/json' \
--data '{
        "name": "test4"
    }'
```

### Get all users

```shell
curl --location 'http://localhost:8080/api/app-user'
```
