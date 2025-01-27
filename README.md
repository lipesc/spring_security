em progresso..;


# Gerenciador de Tarefas - Spring Boot

## Descrição

projeto é um gerenciador de tarefas simples, com registro de usuario e validação com jwt para demonstrar conceitos de backend:

CRUD (Create, Read, Update, Delete) de tarefas\
Autenticação JWT com Spring Security\
Persistência de dados com Spring Data JPA e MySQL\
HTML/javascript  
Boas práticas de segurança

referencias usadas:  
https://www.baeldung.com/java-auth0-jwt  
https://medium.com/@felipeacelinoo/protegendo-sua-api-rest-com-spring-security-e-autenticando-usu%C3%A1rios-com-token-jwt-em-uma-aplica%C3%A7%C3%A3o-d70e5b0331f9  
chatgpt spring framework agent, nao estava entendo como usar o cors do spring e como usar o token gerado no endpoint login em outro endpoint tarefas 

## Tecnologias Utilizadas
 Spring Data JPA
 MySQL
 Spring Security
 java-auth0-jwt dependência
 aws elastic beanstalk
 aws rds mysql
# domain ativo
http://fespring.us-east-1.elasticbeanstalk.com/doc
**desativado devido ao preço

![image](https://github.com/user-attachments/assets/b4c54655-5fd1-4d13-b6e2-a45e0831367c)


## Funcionalidades
Cadastro de usuário\
Login com autenticação JWT\
Criação de tarefas\
Listagem de tarefas\
Atualização de tarefas (título, descrição, status)\
Exclusão de tarefas\
Marcar tarefas pendente,em andamento,concluídas.


## Deploy e Testes com AWS Elastic Beanstalk e GitHub Actions

Este projeto foi implantado e testado usando AWS Elastic Beanstalk, RDS mysql e GitHub Actions.

# Deploy no AWS Elastic Beanstalk

*Configuração do Ambiente:*
   Criado um novo ambiente no AWS Elastic Beanstalk e RDS mysql.
   Configurado o ambiente para executar o aplicativo Spring Boot.

*Deploy da Aplicação:*
    Empacotado o código da aplicação como um arquivo JAR.
    Feito o upload do arquivo JAR para o AWS Elastic Beanstalk.

# Testes com GitHub Actions

*Configuração das Credenciais AWS:*  
   credenciais AWS, configuradas no GitHub Secrets.  

*Criação de Workflow de CI/CD:*  
  Criado um workflow `.github/workflows/main.yml`  
  jobs: Testes Configuração do JDK e execução com Maven, configuração das credenciais AWS, jar do código e deploy no AWS Elastic Beanstalk.
