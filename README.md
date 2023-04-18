# The Demo of Read/Write Split with SpringBoot

## Run Applications
- 
- Startup the Database.

```shell
docker-compose up -d
```

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
