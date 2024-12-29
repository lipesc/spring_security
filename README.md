em progresso..;


# Gerenciador de Tarefas - Spring Boot

## Descrição

projeto é um gerenciador de tarefas simples, com registro de usuario e validação com jwt para demonstrar conceitos de backend:

CRUD (Create, Read, Update, Delete) de tarefas\
Autenticação JWT com Spring Security\
Persistência de dados com Spring Data JPA e MySQL\
HTML/javascript\
Boas práticas de segurança\

## Tecnologias Utilizadas
- Spring Data JPA
- MySQL
- Spring Security oauth2
- aws elastic beanstalk
- aws rds mysql
# domain ativo
http://fespring.us-east-1.elasticbeanstalk.com/doc

## Funcionalidades
Cadastro de usuário\
Login com autenticação JWT\
Criação de tarefas\
Listagem de tarefas\
Atualização de tarefas (título, descrição, status)\
Exclusão de tarefas\
Marcar tarefas pendente,em andamento,concluídas.


## Deploy e Testes com AWS Elastic Beanstalk e GitHub Actions

Este projeto foi implantado e testado usando AWS Elastic Beanstalk, RDS mysql e GitHub Actions. Abaixo estão os passos básicos que foram seguidos:

### Deploy no AWS Elastic Beanstalk

*Configuração do Ambiente:*
   - Criado um novo ambiente no AWS Elastic Beanstalk e RDS mysql.
   - Configurado o ambiente para executar o aplicativo Spring Boot.

*Deploy da Aplicação:*
   - Empacotado o código da aplicação como um arquivo JAR.
   - Feito o upload do arquivo JAR para o AWS Elastic Beanstalk.

### Testes com GitHub Actions

*Configuração das Credenciais AWS:*
   - As credenciais da AWS foram configuradas como GitHub Secrets (`AWS_ACCESS_KEY_ID` e `AWS_SECRET_ACCESS_KEY`).

*Criação de Workflow de CI/CD:*
   - Criado um workflow YAML no diretório `.github/workflows/main.yml` com os seguintes jobs:
     - Testes: Configuração do JDK e execução dos testes com Maven.
     - Deploy: Configuração das credenciais AWS, empacotamento do código e deploy no AWS Elastic Beanstalk.
