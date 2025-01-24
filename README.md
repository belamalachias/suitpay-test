# Product Manager API

## Descrição
Esta API gerencia produtos e categorias de um sistema. Ela foi construída com Spring Boot e oferece endpoints para criação, atualização, exclusão e listagem de produtos e categorias.

## Endpoints
### Categoria
- **GET /api/category** - Retorna todas as categorias
- **GET /api/category/{id}** - Retorna uma categoria específica pelo ID
- **POST /api/category** - Cria uma nova categoria
- **PUT /api/category/{id}** - Atualiza uma categoria existente
- **DELETE /api/category/{id}** - Exclui uma categoria pelo ID
### Produto
- **GET /api/product** - Retorna todos os produtos
- **GET /api/product/{id}** - Retorna um produto específico pelo ID
- **GET /api/product/filtered** - Retorna produtos filtrados por nome, preço e quantidade
- **GET /api/product/category/{categoryId}** - Retorna produtos de uma categoria específica
- **POST /api/product** - Cria um novo produto
- **PUT /api/product/{id}** - Atualiza um produto existente
- **DELETE /api/product/{id}** - Exclui um produto pelo ID


### 1. Como Rodar o Projeto
- Clone o repositório;
- Acesse a pasta do projeto;
- Abra o terminal do Gradle;
- Execute o comando: gradle bootRun

### 3. Acessando o Swagger UI
- Após executar a aplicação, você pode acessar a documentação interativa pelo Swagger e testar os endpoints da API:
	http://localhost:8080/swagger-ui.html



	

