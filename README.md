# Projeto Backend - Java/Spring
Este projeto constitui a parte de backend do desafio, onde será realizada a comunicação com o banco de dados e a geração de token para habilitar o acesso do usuário à tela de Carros e informações sobre sua conta.

## Estórias de Usuário

1. **[BCKD-1] Criação da estrutura Spring Boot do sistema, configuração do banco de dados e do pom.xml**
   - [BCKDS-11] Necessário levantar o sistema para entender se a configuração foi realizada com sucesso;

2. **[BCKD-2] Criação das classes de entity, repository, service e controller**
   - [BCKDS-21] Criação das classes e implementação das configurações para o funcionamento das rotas;
   - [BCKDS-22] Implementação dos métodos no service e no controller, aplicando as regras básicas de negócio e armazenamento no banco de dados;
   - [BCKDS-23] Teste através do Postman das URLs construídas;

3. **[BCKD-3] Configuração do Swagger**
   - [BCKDS-31] Ajustes de erros que apareceram durante a execução das requisições;

4. **[BCKD-4] Implementação da conexão com o frontend**
   - [BCKDS-41] Criar a configuração de CORS;
   - [BCKDS-42] Verificar se as URLs estão sendo chamadas de maneira correta;
   - [BCKDS-43] Ajustes nos endpoints para fazer as requisições;
   - [BCKDS-44] Ajustes nos objetos em relação ao tratamento dos dados que entram na aplicação;

5. **[BCKD-5] Implementação da autenticação JWT**
   - [BCKDS-51] Criação das classes de segurança e geração do token;
   - [BCKDS-52] Criação das classes que fazem a requisição de login e seus retornos;
   - [BCKDS-53] Ajustes de rota para verificar se o usuário está autenticado ou não;
   - [BCKDS-54] Modificação de alguns métodos nos services para realizar a requisição certa no banco;

6. **[BCKD-6] Tratamento de exceções e envio correto dos status**
   - [BCKDS-61] Criar as exceções necessárias;
   - [BCKDS-62] Ajustar os status que retornam de acordo com as exceções;

7. **[BCKD-7] Deploy do sistema utilizando AWS**
   - [BCKDS-71] Atualizar o caminho que irá receber do front para realizar a integração correta;

8. **[BCKD-8] Criação dos testes unitários**
   - [BCKDS-81] Criação dos mocks para o banco de dados;
   - [BCKDS-82] Execução dos testes.
  
_Codificação das estórias:_
   - _[BCKD1]: BCKD -> o código para identificação | 1 -> o número da estória_
   - _BCKDS-11]: BCKD -> indica que é uma subtarefa da estória | 11 -> numeração de subitem, ex 1.1 = 11_

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
