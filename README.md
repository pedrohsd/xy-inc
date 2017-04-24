# xy-inc

Aplicações necessárias para construir e executar a aplicação:

- JDK 8
- Maven 3
- MongoDB

Para instalar o MongoDB, visite o site https://www.mongodb.com/download-center#community. Ou use o comando abaixo para usar via Docker:
    
    docker run --name mongo-baas -p 27017:27017 -d mongo
    

Com a base de dados criada, executar o seguinte comando para rodar os testes automáticos:

	mvn clean test

Para construir e realizar deploy da aplicação executar o seguinte comando:

	mvn clean spring-boot:run

A aplicação estará disponível na porta 8080.
Para criar um modelo, primeiramente é necessário criar a estrutura do mesmo.
Abaixo estão alguns exemplos de como usar a API:

listar todos as estruturas de modelos cadastrados:
	
	GET http://localhost:8080/baas/api/metamodel

criar nova estrutura de modelo:
	
	POST localhost:8080/baas/api/metamodel
	
	payload (json):
	{
        "name": "Imóvel",
        "metaAttributes": [
          {
            "name": "name",
            "type": "STRING"
          },
          {
            "name": "number",
            "type": "INTEGER"
          },
          {
            "name": "builtAt",
            "type": "DATE"
          },
          {
            "name": "price",
            "type": "DECIMAL"
          }
        ]
    }

deletar estrutura de modelo:
	
	DELETE localhost:8080/baas/api/metamodel/{id}
	http://localhost:8080/baas/api/metamodel/58fcf2449063084b9ee0caf9


Após criada a estrutura, pode-se manipular os modelos livremente.
Abaixo estão alguns exemplos de como interagir com a API, usando o model Imóvel:

listar todos os imóveis:

    GET http://localhost:8080/baas/api/model/Imóvel
    
criar novo imovel:

    POST http://localhost:8080/baas/api/model/Imóvel
    {
      "name": "Casa do Fulano",
      "number": 3000,
      "builtAt": "2011-04-08T09:00:00.000",
      "price": 4300.5324
    }
    
atualizar imóvel:

    PUT http://localhost:8080/baas/api/model/Imóvel/58fd54e6906308195f297242
        {
          "id": "58fd54e6906308195f297242",
          "name": "Casa do Beltrano,
          "number": 3001,
          "builtAt": "2011-04-08T09:00:00.000",
          "price": 4300.5324
        }