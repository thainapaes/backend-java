# Projeto Backend - Java/Spring
Este projeto constitui a parte de backend do desafio, onde será realizada a comunicação com o banco de dados e a geração de token para habilitar o acesso do usuário à tela de Carros e informações sobre sua conta.

## Estórias de Usuário

1. **Criação da estrutura Spring Boot do sistema, configuração do banco de dados e do pom.xml**
   - 1.1. Necessário levantar o sistema para entender se a configuração foi realizada com sucesso;

2. **Criação das classes de entity, repository, service e controller**
   - 2.1. Criação das classes e implementação das configurações para o funcionamento das rotas;
   - 2.2. Implementação dos métodos no service e no controller, aplicando as regras básicas de negócio e armazenamento no banco de dados;
   - 2.3. Teste através do Postman das URLs construídas;

3. **Configuração do Swagger**
   - 3.1. Ajustes de erros que apareceram durante a execução das requisições;

4. **Implementação da conexão com o frontend**
   - 4.1. Criar a configuração de CORS;
   - 4.2. Verificar se as URLs estão sendo chamadas de maneira correta;
   - 4.3. Ajustes nos endpoints para fazer as requisições;
   - 4.4. Ajustes nos objetos em relação ao tratamento dos dados que entram na aplicação;

5. **Implementação da autenticação JWT**
   - 5.1. Criação das classes de segurança e geração do token;
   - 5.2. Criação das classes que fazem a requisição de login e seus retornos;
   - 5.3. Ajustes de rota para verificar se o usuário está autenticado ou não;
   - 5.4. Modificação de alguns métodos nos services para realizar a requisição certa no banco;

6. **Tratamento de exceções e envio correto dos status**
   - 6.1. Criar as exceções necessárias;
   - 6.2. Ajustar os status que retornam de acordo com as exceções;

7. **Deploy do sistema utilizando AWS**
   - 7.1. Atualizar o caminho que irá receber do front para realizar a integração correta;

8. **Criação dos testes unitários**
   - 8.1. Criação dos mocks para o banco de dados;
   - 8.2. Execução dos testes.

## Solução

Para a solução desse problema foi utilizado Java 17 e o framework do Spring para construção da aplicação. Como solicitado a criação de uma API Rest foi utilizado a arquitetura "Controller-Service-Repository" para melhor entendimento do fluxo do projeto e divisão das funções corretamente.

Explicando os packages criados:

- **controller:** Todos os controllers estão nessa pasta e neles encontra-se a lógica de receber a requisição HTTP do frontend, pode ter alguma tratativa, porém, seu foco é receber o request e repassar para o service;
- **dto:** Foi preciso criar as DTOs para lidar melhor com as requisições que entram no sistema depois da configuração da JWT. São classes do tipo "record", foi escolhido esse tipo de classe pela fácil manipulação dos dados;
- **entities:** Todas as entidades que serão registradas no banco;
- **exceptions:** As exceções que são exibidas no log do backend;
- **infra:** Arquivos de configuração do JWT;
- **repository:** Os repositórios que fazem a manipulação dos dados no banco;
- **services:** As classes services criadas para validação de dados e conexão com o repository para manipulação no banco.

A nomenclatura dos métodos e das classes está predominantemente em inglês por conta das variáveis que foram passadas no documento do desafio. Para o funcionamento da criação do token de login, a pessoa que irá realizar a execução do sistema pode cadastrar a variável de ambiente JWT_SECRET. Não é obrigatório, pois o sistema está configurado para ter uma chave default caso não encontre essa variável.
