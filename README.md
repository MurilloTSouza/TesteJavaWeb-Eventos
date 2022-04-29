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

| Função       | Método | Caminho              | Parâmetros   | Body (exemplo)                                                                                     |
|--------------|--------|----------------------|--------------|----------------------------------------------------------------------------------------------------|
| Cadastro     | POST   | /api/evento/cadastro | ---          | { "nome": "Evento de Natal", "vagas": 3, "inicio": "2022-12-24T18:30", "fim": "2022-12-25T03:30" } |
| Listar Todos | GET    | /api/evento/         | ---          | ---                                                                                                |
| Buscar       | GET    | /api/evento/{id}     | ID do evento | ---                                                                                                |

### -> Usuário

| Função               | Método | Caminho                | Parâmetros    | Body (exemplo)                      |
|----------------------|--------|------------------------|---------------|-------------------------------------|
| Cadastro             | POST   | /api/usuario/cadastro  | ---           | { "nome": "John Doe" }              |
| Buscar               | GET    | /api/usuario/{id}      | ID do usuário | ---                                 |
| Inscrever em Evento  | POST   | /api/usuario/inscricao | ---           | { "evento_id": 1, "usuario_id": 2 } |
| Cancelar Inscrição   | DELETE | /api/usuario/inscricao | ---           | { "evento_id": 1, "usuario_id": 2 } |
| Participar do Evento | POST   | /api/usuario/entrar    | ---           | { "evento_id": 1, "usuario_id": 2 } |

## :pencil: Observações. :

- O desafio foi entregue novamente no dia 29 de Abril (sexta-feira), desta vez obedecendo todos os requisitos,
  pois havia dúvidas sobre a data limite e por precaução foi entregue parcialmente no dia anterior (quinta-feira).
- Embora não tenha sido especificado dentre os requisitos, foi optado em possibilitar que o usuário participe
  apenas de 1 evento por vez. Ao entrar em outro evento, o usuário será automaticamente remotido do evento anterior.
- A persistência dos dados não será mantida após o encerramento ou reinicialização da aplicação.
- Para manter a estrutura dos dados semelhante ou enunciado do teste, foi escolhido desenvolver o projeto em
  português (na maior parte).
