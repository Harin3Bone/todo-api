# TODO Mock API

![Java](https://img.shields.io/badge/Java-E32C2E?logo=Java&style=flat&logoColor=ffffff)&nbsp;
![SpringBoot](https://img.shields.io/badge/Spring_Boot-6DB33F?&style=flat&logo=spring&logoColor=F7F7F7)&nbsp;
![Docker](https://img.shields.io/badge/Docker-2496ED?&style=flat&logo=docker&logoColor=ffffff)&nbsp;
![MySql](https://img.shields.io/badge/MySql-F7F7F7?&style=flat&logo=mysql&logoColor=336791)&nbsp;

## Description

This project was created for front-end developers
who want a mock API to run on the TODO website.

## Prerequisite

1. Docker
2. Docker compose

## Get Started

Please running command below

```bash
docker-compose up -d
```

> **NOTE**
>
> If you want to change default value 
> please create your own `.env` file

## Default Value

| Key                 |            Value |          Description |
|:--------------------|-----------------:|---------------------:|
| SERVICE_NAME        | todo_application |   App container name |
| SERVICE_PORT        |             8080 |     App port binding |
| SERVICE_SECRET      |       usersecret |   App API-Key secret |
| MYSQL_NAME          |    todo_database | MySQL Container name |
| MYSQL_PORT          |             3306 |   MySQL port binding |
| MYSQL_VERSION       |           8.0.31 |  MySQL image version |
| MYSQL_ROOT_PASSWORD |           123456 |  MySQL root password |
| MYSQL_DATABASE      |             todo |        Database Name |
| MYSQL_USER          |    todo_username |    Database username |
| MYSQL_PASSWORD      |    todo_password |    Database password |
| TIMEZONE            |   "Asia/Bangkok" |   Container timezone |

## Swagger (OpenAPI)

After started application complete, Please enter `localhost:{app_port}/todo/swagger-ui`

## Contributor

![Harin3Bone](https://img.shields.io/badge/Harin3Bone-181717?style=flat&logo=github&logoColor=ffffff)