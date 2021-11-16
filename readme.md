# DSCatalog

Projeto de um catálogo de produtos que podem ser agrupados em categorias.

## Como rodar o backend usando docker

```
docker run -d -p 8080:8080 --name dscatalog julianocanuto/dscatalog:0.0.1-SNAPSHOT
```
## Como rodar diretamente no terminal (sem Docker)

```
mvn spring-boot:run
```
## Endpoints

```
GET /products       Retorna lista de produtos
GET /categories     Retorna lista de categorias
GET /product/1      Retorna produto com id igual a 1
```