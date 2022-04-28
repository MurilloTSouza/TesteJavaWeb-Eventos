<!-- header -->
<h1 align="center">Eventos</h1>
<p align="center">Teste para processo seletivo Java WEB - BeHOH.</P>
<p align="center">
  <i>API com o intuíto de cadastrar eventos e usuários,assim como inscrever 
     usuários e registrar sua entrada nos eventos.</i>
</p>

## :zap: Executando o projeto
O projeto pode ser executado com as seguintes ferramentas abaixo.

Por padrão o sistema será
executada utilizando por padrão a **PORTA 8080**.


#### -> MVN
O Projeto pode ser executado utilizando as ferramentas Maven embutida no projeto.

- Na raíz do projeto, execute: ` ./mvnw spring-boot:run `.

- Utilizando uma porta alternativa: `./mvnw spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=[PORTA]'`

### -> Docker
Caso tenha preferência, o projeto pode ser executado com Docker.

- Na raiz do projeto, construa a imagem: `docker build -t eventos-behoh .`

- Executando o container: `docker run -dp [PORTA]:8080 eventos-behoh`

É necessário esperar alguns segundos até que a aplicação seja iniciada.

## :mag: Utilizando a API

### -> Documentação

A API está documenta utilizando o framework **Swagger** para facilitar o entendimento
assim como também o uso.

A documentação pode ser encontrada através do caminho http://localhost:8080/swagger-ui.html.
*(considerando que a aplicação foi executada na porta padrão 8080).*

De qualquer forma, segue uma tabela dos caminhos disponiveis até então:

### -> Evento

| Função   | Método | Caminho              | Parâmetros   | Body (exemplo)                                                                                     |
|----------|--------|----------------------|--------------|----------------------------------------------------------------------------------------------------|
| Cadastro | POST   | /api/evento/cadastro | ---          | { "nome": "Evento de Natal", "vagas": 3, "inicio": "2022-12-24T18:30", "fim": "2022-12-25T03:30" } |
| Listar   | GET    | /api/evento/         | ---          | ---                                                                                                |
| Buscar   | GET    | /api/evento/{id}     | ID do evento | ---                                                                                                |

### -> Usuário

| Função              | Método | Caminho                | Parâmetros    | Body (exemplo)                      |
|---------------------|--------|------------------------|---------------|-------------------------------------|
| Cadastro            | POST   | /api/usuario/cadastro  | ---           | { "nome": "John Doe" }              |
| Buscar              | GET    | /api/usuario/{id}      | ID do usuário | ---                                 |
| Inscrever em Evento | POST   | /api/usuario/inscricao | ---           | { "evento_id": 0, "usuario_id": 0 } |

## :pencil: Considerações finais. :

Embora em um meio de produção a prioridade seja a entrega de todos os requisitos no prazo,
durante o desenvolvimento deste projeto o meu foco foi utilizar diferentes tecnologias e
explorar mais o java (como documentações, validações, criação de anotações, etc), visando
buscar um diferencial, o que ocasionou na falta de alguns requisitos até o momento da entrega
(como a participação de um usuário em um evento).
