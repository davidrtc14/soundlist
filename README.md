# SoundList 🎵

SoundList é uma API REST para gerenciamento de playlists musicais, desenvolvida com **Spring Boot 3**. A aplicação permite criar e gerenciar playlists e as músicas que as compõem, seguindo boas práticas de arquitetura em camadas, validação de dados e separação de responsabilidades.

## Descrição

A API expõe endpoints completos para o gerenciamento de **playlists** e **músicas**, com suporte a paginação, validações automáticas e tratamento centralizado de erros. O projeto segue uma arquitetura em camadas (Model, Repository, Service, Controller e DTOs), garantindo que nenhuma entidade JPA trafegue diretamente nas requisições ou respostas da API.

---

## Tecnologias

- **Java 25**
- **Spring Boot 3**
- **Spring Data JPA** com Hibernate
- **PostgreSQL**
- **MapStruct** para conversão entre entidades e DTOs
- **Lombok**
- **Bean Validation**
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
└── exception/        # Tratamento centralizado de erros
```

---

## Endpoints

### Playlists

| Método   | Endpoint                    | Descrição                              |
|----------|-----------------------------|----------------------------------------|
| `POST`   | `/playlists`                | Criar playlist                         |
| `GET`    | `/playlists`                | Listar todas (paginado)                |
| `GET`    | `/playlists/{id}`           | Buscar por ID                          |
| `GET`    | `/playlists/{id}/musics`    | Listar músicas de uma playlist (paginado) |
| `PUT`    | `/playlists/{id}`           | Atualizar                              |
| `PATCH`  | `/playlists/{id}`           | Atualização parcial                    |
| `DELETE` | `/playlists/{id}`           | Remover (apaga músicas em cascata)     |

### Músicas

| Método   | Endpoint         | Descrição              |
|----------|------------------|------------------------|
| `POST`   | `/musics`        | Adicionar música       |
| `GET`    | `/musics`        | Listar todas (paginado)|
| `GET`    | `/musics/{id}`   | Buscar por ID          |
| `PUT`    | `/musics/{id}`   | Atualizar              |
| `PATCH`  | `/musics/{id}`   | Atualização parcial    |
| `DELETE` | `/musics/{id}`   | Remover                |

**Paginação** disponível via parâmetros de query:
```
GET /playlists?page=0&size=10&sort=name,asc
```

---

## Requisitos

- **Java 25**
- **PostgreSQL**

### Configuração do banco de dados

Escolha uma das opções abaixo para rodar o PostgreSQL:

**Opção 1 — Instalação local**

Baixe e instale diretamente pelo site oficial: [postgresql.org/download](https://www.postgresql.org/download/)

Ou via gerenciador de pacotes:

```bash
# macOS
brew install postgresql@16

# Ubuntu/Debian
sudo apt install postgresql

# Fedora/RHEL
sudo dnf install postgresql-server
```

**Opção 2 — Docker**

```bash
docker run -d \
  --name soundlist-postgres \
  -e POSTGRES_USER=user \
  -e POSTGRES_PASSWORD=mypassword123 \
  -e POSTGRES_DB=soundlist_db \
  -p 5432:5432 \
  postgres:latest
```

---

## Configuração

As credenciais do banco estão em `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/soundlist_db
    username: user
    password: mypassword123
  jpa:
    hibernate:
      ddl-auto: update
```

---

## Como rodar

```bash
# Clone o repositório
git clone https://github.com/davidrtc14/soundlist.git)https://github.com/davidrtc14/soundlist.git
cd soundlist

# Suba o banco de dados (caso use Docker)
docker start soundlist-postgres

# Rode a aplicação
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.
