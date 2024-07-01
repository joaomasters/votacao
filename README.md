# Voting App

## Descrição
Aplicação para gerenciar sessões de votação em cooperativas, onde cada associado possui um voto.

## Pré-requisitos
- Java 17
- Maven

- ## Features

- Criar uma nova pauta
- Abrir uma sessão de votação para uma pauta
- Receber votos dos associados
- Contar os votos e determinar o resultado da votação

## Integração Externa (Bônus)

O aplicativo se integra com um sistema externo para verificar a elegibilidade para votar com base no CPF.

## Configuração do Projeto
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/voting-app.git
   cd voting-app

mvn clean install
mvn spring-boot:run


Acesse a aplicação:

- A aplicação estará disponível em http://localhost:8080.
- Acesse o console do H2 em http://localhost:8080/h2-console.

Cadastrar Pauta

POST /api/v1/pautas
Body: { "descricao": "Descrição da pauta" }
Response: 201 Created
Abrir Sessão de Votação

POST /api/v1/sessoes/pautas/{pautaId}
Body: { "dataFim": "2024-07-01T10:00:00" }
Response: 201 Created
Receber Voto

POST /api/v1/votos/sessoes/{sessaoId}
Body: { "associadoId": 1, "voto": true }
Response: 201 Created
Resultado da Votação

GET /api/v1/votos/sessoes/{sessaoId}/contagem
Response: 200 OK
Estrutura de Pastas
src/main/java/com/example/votingapp
VotingAppApplication.java
controller
model
repository
service
dto
exception
