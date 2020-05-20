# MyERP [![Build Status](https://travis-ci.org/GLescroel/myErp.svg?branch=master)](https://travis-ci.org/GLescroel/myErp)[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=GLescroel_myErp&metric=coverage)](https://sonarcloud.io/dashboard?id=GLescroel_myErp)

## Organisation du répertoire

*   `doc` : documentation
*   `docker` : répertoire relatifs aux conteneurs _docker_ utiles pour le projet
    *   `dev` : environnement de développement
*   `src` : code source de l'application


## Environnement de développement

Les composants nécessaires lors du développement sont disponibles via des conteneurs _docker_.
L'environnement de développement est assemblé grâce à _docker-compose_
(cf docker/dev/docker-compose.yml).

Il comporte :

*   une base de données _PostgreSQL_ contenant un jeu de données de démo (`postgresql://127.0.0.1:9032/db_myerp`)



### Lancement

    cd docker/dev
    docker-compose up


### Arrêt

    cd docker/dev
    docker-compose stop


### Remise à zero

    cd docker/dev
    docker-compose stop
    docker-compose rm -v
    docker-compose up


### Lancement des tests incluant les tests d'intégration
    à partir de la racine src: 
    mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Ptest-business,test-consumer

### Génération du site Maven
    à partir de la racine src : 
    mvn package site site:stage