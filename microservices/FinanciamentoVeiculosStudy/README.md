# financiamento-veiculos-study-microservice
API to simulate financing values

This project is a study case of a complete application structure

### API Documentation

http://localhost:8080/v2/api-docs  
http://localhost:8080/swagger-ui.html

## Pre-Requires
- JDK 8
- Docker ([Install](https://docs.docker.com/v17.09/engine/installation/linux/docker-ce/ubuntu/ "Install") | [Configure](https://docs.docker.com/v17.09/engine/installation/linux/linux-postinstall/ "Configure"))

## Pre-Requires Local
- Set Git Pre-Push Hook to execute unit and component tests before every git push

	`cp pre-push ../../.git/hooks &&
     chmod ug+x ../../.git/hooks/pre-push`

- SQL Server setup
    - Tag the image locally with the name 'sql-server'
    
        `cd misc/docker/sql_server && make build`
    
    - Init the container and use an exclusive volume to keep your database permanent data
        
        `cd misc/docker/sql_server && make run`
        
    - Start the database container
    
        `docker start sql-server`
        
    - Stop the database container
    
        `docker stop sql-server`
        
    - To configure initial data and users, add shellscripts or sql files on folder:
        
        `misc/docker/sql_server/setup_scripts`

## Commands
- Build project

    `./mvnw clean compile`

- Test project

    `./mvnw test`

- Start project

    `./mvnw spring-boot:run`

- Start project with local profile

    `./mvnw spring-boot:run -Plocal`

## Liquibase
- Configurations from connection liquibase insert in src/main/resource/liquibase.yaml
- Execute liquibase to update local

    `./mvnw compile liquibase:update -Plocal`
    
- Execute liquibase to local update ddl only
    
    `./mvnw compile liquibase:update -Pddl`
    
- Execute liquibase to local update dml only
    
    `./mvnw compile liquibase:update -Pdml`

- Execute liquibase to updateTestingRollback local
    
    `./mvnw compile liquibase:updateTestingRollback -Plocal`
    
- Execute liquibase to rollback by step local
    
    `./mvnw compile liquibase:rollback -Dliquibase.rollbackCount=1 -Plocal`
    
## Automated testing

 Prerequisites to perform automated test of car finance flow

- Chromedriver should be in path
  
    `src/main/resources/drivers/chromedriver`
    
    - obs: chromedriver V84.0
    - obs: chormedriver should have the same version as your current browser

- Run init.sh file on root directory 

    `./init.sh`

- All containers must be up as below

  - `Starting sql-server ... done`
  - `Starting microservice ... done`
  - `Starting bff          ... done`
  - `Starting front-end    ... done`

- Remove ~ from tag field on class SimularFinanciamentoTest

    - `com/financiamentos/veiculosstudy/cucumber/SimularFinanciamentoTest.java`
    - `tags = "@SimularFinanciamento"`

- Execute SimularFinanciamentoTest file as a test

