# SoundList 🎵

SoundList é uma API REST para gerenciamento de playlists musicais, desenvolvida com **Spring Boot 3**. A aplicação permite criar e gerenciar playlists e as músicas que as compõem, seguindo boas práticas de arquitetura em camadas, validação de dados e separação de responsabilidades.

---

## Tecnologias

- **Java 21**
- **Spring Boot 3.5**
- **Spring Data JPA** com Hibernate
- **PostgreSQL 16**
- **Docker** (para o banco de dados)
- **MapStruct** — conversão entre entidades e DTOs
- **Lombok**
- **Bean Validation**
- **SpringDoc OpenAPI** — documentação interativa (Swagger UI)
- **Maven**

---

## Arquitetura

```
src/main/java/com/p7/soundlist/
├── controller/       # Camada HTTP — recebe requisições e retorna respostas
├── service/          # Regras de negócio e orquestração
├── repository/       # Acesso ao banco via Spring Data JPA
├── model/            # Entidades JPA mapeadas para o banco de dados
├── mapper/           # Conversão entre entidade e DTO via MapStruct
├── dtos/             # Objetos de transferência (Request e Response)
├── exception/        # Tratamento centralizado de erros
└── config/           # Configurações da aplicação (OpenAPI, etc.)
```

---

## Pré-requisitos

- **Java 21**
- **Docker** e **Docker Compose**
- **Maven** (ou usar o wrapper `./mvnw` incluso no projeto)

---

## Como rodar

### 1. Clonar o repositório

```bash
git clone https://github.com/davidrtc14/soundlist.git
cd soundlist
```

### 2. Configurar as variáveis de ambiente

O projeto usa dois arquivos `.env`: um para o banco (Docker) e um para a aplicação.

**Banco de dados — `infra/.env`:**

```bash
cp infra/.env.example infra/.env
```

Edite `infra/.env` com as credenciais desejadas:

```env
POSTGRES_DB=soundlist_db
POSTGRES_USER=usuario_teste
POSTGRES_PASSWORD=senha_teste
```

**Aplicação — `.env` (raiz do projeto):**

```bash
cp .env.example .env
```

Edite `.env` garantindo que as credenciais batem com as do `infra/.env`:

```env
DB_URL=jdbc:postgresql://localhost:5432/soundlist_db
DB_USERNAME=usuario_teste
DB_PASSWORD=senha_teste
```

### 3. Subir o banco de dados

```bash
cd infra
docker compose up -d
```

Verifique se o container está rodando:

```bash
docker ps
```

Você deve ver `postgres-test` com status `Up`.

### 4. Rodar a aplicação

De volta à raiz do projeto:

```bash
cd ..
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

> **Windows:** use `mvnw.cmd spring-boot:run` no lugar de `./mvnw spring-boot:run`.

### 5. Parar o banco de dados

Quando terminar:

```bash
cd infra
docker compose down
```

Para remover também o volume com os dados:

```bash
docker compose down -v
```

---

## Documentação interativa

Com a aplicação rodando, acesse o Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

---

## Endpoints

### Playlists — `/api/playlists`

| Método   | Endpoint                      | Descrição                                 |
|----------|-------------------------------|-------------------------------------------|
| `POST`   | `/api/playlists`              | Criar playlist                            |
| `GET`    | `/api/playlists`              | Listar todas (paginado)                   |
| `GET`    | `/api/playlists/{id}`         | Buscar por ID                             |
| `GET`    | `/api/playlists/{id}/musics`  | Listar músicas de uma playlist (paginado) |
| `PUT`    | `/api/playlists/{id}`         | Atualizar completamente                   |
| `PATCH`  | `/api/playlists/{id}`         | Atualização parcial                       |
| `DELETE` | `/api/playlists/{id}`         | Remover (apaga músicas em cascata)        |

### Músicas — `/api/musics`

| Método   | Endpoint            | Descrição               |
|----------|---------------------|-------------------------|
| `POST`   | `/api/musics`       | Adicionar música        |
| `GET`    | `/api/musics`       | Listar todas (paginado) |
| `GET`    | `/api/musics/{id}`  | Buscar por ID           |
| `PUT`    | `/api/musics/{id}`  | Atualizar completamente |
| `PATCH`  | `/api/musics/{id}`  | Atualização parcial     |
| `DELETE` | `/api/musics/{id}`  | Remover                 |

### Paginação

Todos os endpoints de listagem suportam os parâmetros de query:

```
GET /api/playlists?page=0&size=10&sort=name,asc
```

---

## Exemplos de requisição

### Criar playlist

```http
POST /api/playlists
Content-Type: application/json

{
  "name": "Minha Playlist",
  "description": "Descrição opcional"
}
```

### Adicionar música

```http
POST /api/musics
Content-Type: application/json

{
  "title": "Nome da Música",
  "artist": "Nome do Artista",
  "genre": "Rock",
  "duration": 210,
  "playlistId": 1
}
```

### Atualização parcial de música

```http
PATCH /api/musics/1
Content-Type: application/json

{
  "duration": 195
}
```

---

## Tratamento de erros

Todos os erros retornam o seguinte formato:

```json
{
  "status": "400",
  "errors": {
    "campo": "mensagem de erro"
  },
  "localDateTime": "2026-06-02T16:35:04.889"
}
```

| Status | Situação                                              |
|--------|-------------------------------------------------------|
| `400`  | Dados inválidos, tipo incorreto ou campo obrigatório ausente |
| `404`  | Recurso não encontrado                                |
| `500`  | Erro interno no servidor                              |