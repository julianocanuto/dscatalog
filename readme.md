# DSCatalog

Projeto de um catálogo de produtos que podem ser agrupados em categorias.

## Como rodar o backend usando docker

```
docker run -d -p 8080:8080 --name dscatalog julianocanuto/dscatalog:0.0.2-SNAPSHOT
```
## Como rodar diretamente no terminal (sem Docker)

```
mvn spring-boot:run
```
## Endpoints

Os endpoints estão acessíveis através do Swagger-ui no endereço: http://localhost:8080/swagger-ui.html#/

![endpoints](https://user-images.githubusercontent.com/17866411/143489728-536e24a7-6669-4ccc-bb31-df8a4226badc.png)