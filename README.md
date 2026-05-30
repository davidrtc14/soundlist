# SoundList 🎵

SoundList é um serviço de gerenciamento de playlists musicais com API REST desenvolvido em **Spring Boot**. A aplicação permite criar e gerenciar playlists e as músicas que as compõem, seguindo boas práticas de arquitetura em camadas, validação de dados e separação de responsabilidades.

## Descrição

A API expõe endpoints para o gerenciamento completo de **playlists** e **músicas**, com suporte a paginação, validações automáticas e tratamento centralizado de erros. O projeto segue uma arquitetura em camadas (Model, Repository, Service, Controller e DTOs), garantindo que nenhuma entidade JPA trafegue diretamente nas requisições ou respostas da API.

---

## Requisitos

Antes de rodar o projeto, certifique-se de ter instalado:

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
docker run -d --name soundlist-postgres -e POSTGRES_USER=user -e POSTGRES_PASSWORD=mypassword123 -e POSTGRES_DB=soundlist_db -p 5432:5432 postgres:latest
```

Em seguida, configure as credenciais no arquivo `src/main/resources/application.yaml`:

```yaml
  datasource:
    url: jdbc:postgresql://localhost:5432/soundlist_db
    username: user
    password: mypassword123
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
```
