#!/bin/bash

echo '------------------------------------------------------------------------'
echo 'Stopping containers...'
echo '------------------------------------------------------------------------'
docker-compose stop

echo '------------------------------------------------------------------------'
echo 'Building microservice...'
echo '------------------------------------------------------------------------'
cd microservices/FinanciamentoVeiculosStudy/
./mvnw -Pdev clean package

echo '------------------------------------------------------------------------'
echo 'Building bff...'
echo '------------------------------------------------------------------------'
cd ../../front-end/bff/financiamento-veiculos-study-bff/
npm install && npm run build

echo '------------------------------------------------------------------------'
echo 'Building front-end...'
echo '------------------------------------------------------------------------'
cd ../../financiamento-veiculos-study-web/
npm install && npm run build

echo '------------------------------------------------------------------------'
echo 'Building docker images'
echo '------------------------------------------------------------------------'
cd ../../
docker-compose -f ./docker-compose.yaml up -d

echo '------------------------------------------------------------------------'
echo 'Generating database structure...'
echo '------------------------------------------------------------------------'
cd microservices/FinanciamentoVeiculosStudy/
./mvnw compile liquibase:update -Pdev

