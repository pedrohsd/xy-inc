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
        "name": "Pessoa",
        "metaAttributes": [
          {
            "name": "name",
            "type": "STRING"
          },
          {
            "name": "age",
            "type": "INTEGER"
          }
        ]
    }


deletar estrutura de modelo:
	
	DELETE localhost:8080/baas/api/metamodel/{id}
	http://localhost:8080/baas/api/metamodel/58fcf2449063084b9ee0caf9


Após criada a estrutura, pode-se manipular os modelos livremente.
Abaixo estão alguns exemplos de como interagir com a API, usando o model Imóvel:

listar todos os imóveis:

    GET http://localhost:8080/baas/api/model/Pessoa
    
criar nova pessoa:

    POST http://localhost:8080/baas/api/model/Pessoa
    {
        "name": "Pedro Henrique",
        "age": 25
    }
    
atualizar pessoa:

    PUT http://localhost:8080/baas/api/model/Pessoa/d03a67ba-99d7-4969-b3fb-b0641f13eb1f
        {
            "name": "Pedro Henrique Silvestre Duarte",
            "age": 25,
            "_id": "d03a67ba-99d7-4969-b3fb-b0641f13eb1f"
        }
        
deletar pessoa:
        
        DELETE http://localhost:8080/baas/api/model/Pessoa/d03a67ba-99d7-4969-b3fb-b0641f13eb1f