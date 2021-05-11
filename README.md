# Star Wars API


O seguinte projeto foi desenvolvido com o intuito de, através de chamadas REST, realizar o CRUD de Planetas do universo do Star Wars.


O projeto foi desenvolvido em Java 11, Spring Boot, tem como banco de dados uma instância simples de MongoDB e contém testes unitários em Junit 4.

As principais funções da **API** são:

- Inserir um **planeta** com os seguintes parâmetros:

  	name: String;
    	climate: String;
    	terrain: String;
    	films: List < Film> 

-  Listar todos os planetas cadastrados;
- Buscar um planeta por nome;
- Buscar um planeta por ID;
- Remover um planeta por ID;
- Atualizar um planeta por ID;
- Listar todos os filmes da saga Star Wars.

# Executando o projeto

É necessário ter uma instalação do [MongoDB](https://www.mongodb.com/try/download/community) e um client para realizar as chamadas REST, recomendo o [Postman](https://www.postman.com) .

A primeira coisa a se fazer é subir o MongoDB, para isso execute o comando abaixo em seu terminal:

    mongod
Uma mensagem semelhante a abaixo deve aparecer no seu terminal:


    "msg":"Waiting for connections"

Após isso, devemos abrir a pasta do projeto, que foi clonada desse repositório, na sua IDE de preferência, recomendo o [IntelliJ](https://www.jetbrains.com/pt-br/idea/download/#section=windows).

Feito isso, basta executar o comando:

    mvnw spring-boot:run
E esperar por uma mensagem semelhante a:

    2021-05-10 21:38:35.368  INFO 8148 --- [  restartedMain] c.s.s.StarWarsPlanetsApplication         : Started StarWarsPlanetsApplication in 2.451 seconds

Podemos também, executar os testes unitários do projeto com o comando:

    mvwn test


# Realizando chamadas REST
Para realizar as chamadas do projeto, é necessário abrir o Postman (ou seu client favorito) e seguir os passos abaixo para cada função:

## Inserindo um planeta
Para inserir um planeta no banco de dados é necessário fazer um **POST** para o endpoint `localhost:8080/api/planets`. passando no body da requisição os dados do planeta a ser incluído, conforme exemplo abaixo:

    {

    "name": "Tatooine",
    
    "climate": "Frio",
    
    "terrain": "Montanhoso"
    
    }

Nessa requisição antes de inserir o planeta na base de dados, pelo **nome**, checamos se o planeta existe na base da [api do Star Wars](https://swapi.dev),  se ele existir,  o planeta será inserido com os dados oficiais do planeta na saga:

    {

    "data": {
    
    "id": "6099d54c8808b057b811a90d",
    
    "name": "Tatooine",
    
    "climate": "arid",
    
    "terrain": "desert",
    
    "films": [
    
    {
    
    "title": "A New Hope",
    
    "episodeId": 4
    
    },
    
    {
    
    "title": "Return of the Jedi",
    
    "episodeId": 6
    
    },
    
    {
    
    "title": "The Phantom Menace",
    
    "episodeId": 1
    
    },
    
    {
    
    "title": "Attack of the Clones",
    
    "episodeId": 2
    
    },
    
    {
    
    "title": "Revenge of the Sith",
    
    "episodeId": 3
    
    }
    
    ]
    
    },
    
    "errors": null
    
    }

Caso o planeta não exista no universo do Star Wars ele será incluído com os dados da request.


## Listando os Planetas

Para listar todos os planetas cadastrados, basta fazer um **GET** para `localhost:8080/api/planets`,  podemos também filtrar por **nome** ou **id** para isso fazemos um **GET** para os endpoints  `localhost:8080/api/planets/name/{name}` ou   `localhost:8080/api/planets/id/{id}` respectivamente.

## Atualizando um planeta por ID

Podemos atualizar um planeta fazendo um **PUT** para `localhost:8080/api/planets/{id}` passando no *body* da requisição apenas os campos que deseja atualizar, caso o ID não exista na base de dados será inserido um novo registro com os dados contidos no *body*.

## Deletando um planeta por ID
Para excluir um planeta da base de dados basta fazer um **DELETE** para `localhost:8080/api/planets/{id}`.

## Listando todos os filmes da saga Star Wars
Também foi criado um endpoint para que possamos obter todos os filmes lançados da saga (até o episódio 6), podemos fazer um **GET** para `localhost:8080/api/films`, o endpoint consome a API da https://swapi.dev e nos devolve os filmes com o número do episódio e o nome.