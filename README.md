# Batch CNAB Processor

Este projeto é uma aplicação para processar arquivos CNAB, normalizar os dados e armazená-los em um banco de dados relacional. Ele utiliza **Spring Batch** para gerenciar o processamento dos arquivos de forma eficiente e escalável. A aplicação também expõe endpoints REST para visualizar as informações processadas agrupadas por loja, incluindo um saldo totalizado por loja.

## Funcionalidades

1. **Processamento com Spring Batch:**
   - O sistema utiliza **Spring Batch** para processar o arquivo CNAB em etapas (steps), garantindo robustez, escalabilidade e controle de falhas.

2. **Endpoint para Upload de Arquivo:**
   - Um endpoint REST permite o upload de arquivos CNAB.

3. **Processamento de Arquivo:**
   - O arquivo é parseado utilizando o Spring Batch para extrair as informações transacionais, que são normalizadas e armazenadas em um banco de dados relacional.

4. **Endpoint para Listar Transações:**
   - Um endpoint REST exibe as transações agrupadas por loja.
   - Inclui o saldo total de cada loja.

## Estrutura do CNAB

| Descrição do campo  | Início | Fim | Tamanho | Comentário |
|---------------------|--------|-----|---------|------------|
| Tipo                | 1      | 1   | 1       | Tipo da transação |
| Data                | 2      | 9   | 8       | Data da ocorrência |
| Valor               | 10     | 19  | 10      | Valor da movimentação (divido por 100 para normalização) |
| CPF                 | 20     | 30  | 11      | CPF do beneficiário |
| Cartão              | 31     | 42  | 12      | Cartão utilizado na transação |
| Hora                | 43     | 48  | 6       | Hora da ocorrência (fuso UTC-3) |
| Dono da loja        | 49     | 62  | 14      | Nome do representante da loja |
| Nome loja           | 63     | 81  | 19      | Nome da loja |

## Tipos de Transações

| Tipo | Descrição                | Natureza | Sinal |
|------|--------------------------|----------|-------|
| 1    | Débito                   | Entrada  | +     |
| 2    | Boleto                   | Saída    | -     |
| 3    | Financiamento            | Saída    | -     |
| 4    | Crédito                  | Entrada  | +     |
| 5    | Recebimento Empréstimo   | Entrada  | +     |
| 6    | Vendas                   | Entrada  | +     |
| 7    | Recebimento TED          | Entrada  | +     |
| 8    | Recebimento DOC          | Entrada  | +     |
| 9    | Aluguel                  | Saída    | -     |

## Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/cauaemanuel/batch-cnab-processor.git
   cd batch-cnab-processor
   ```

2. Configure o ambiente:
   - Certifique-se de ter o **Java 21+** e o **Maven** instalados.
   - Configure o banco de dados no arquivo `application.properties`.

3. Execute o projeto:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Utilize os Endpoints:
   - **Upload do Arquivo CNAB:**
     - Endpoint: `POST /api/upload`
     - Enviar o arquivo CNAB no corpo da requisição como um campo `multipart/form-data`.

   - **Listar Transações:**
     - Endpoint: `GET /api/transactions`
     - Retorna as transações agrupadas por loja, incluindo o saldo total.
