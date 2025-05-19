# Projeto Ecom-test

O projeto tem como intuito realizar requisições com a api de filmes [top-rated](https://developer.themoviedb.org/reference/movie-top-rated-list) e [trending-movies](https://developer.themoviedb.org/reference/trending-movies) para manipular essas informações e trazer resultados como:
1. Média de Nota por Gênero;
2. Quantidade de Filmes por Gênero;
3. Quantidade de Filmes por Ano;
4. Quantos e quais desses filmes estão entre os Trending nas 20 primeiras páginas;

## Ferramentas

- Linguagem de programação: [Java](https://www.java.com/pt-BR/)
- IDE: [IntelliJ](https://www.jetbrains.com/idea/)

## Como utilizar o software

### Requisitos
- java-corretto-11 (java 11.0.27)

### Executando

#### Releases
Na aba [releases](link) baixe o arquivo .jar

##### Linux
- Abra o terminal no diretório que baixou o arquivo e digite o comando: `java -jar ecom-teste.jar`
##### Windows
Há várias maneiras, segue abaixo algumas delas:
  1. Dois cliques no software para executar
  2. Abra o prompt de comando (CMD ou powershell ou de sua preferência) no diretório que se encontra o arquivo e digite o seguinte comando: `java -jar ecom-teste.jar`

#### Código fonte
1. Clone o repositório: `https://github.com/Gustheou/ecom-teste.git`
2. Abra o repositório com a sua IDE ou ferramenta de preferência

   2.1. Intellij -> Abra o projeto e execute a classe Main.java

   2.2. Demais -> Adicione a biblioteca externa ao seu projeto que se encontra em `lib/json-simple-1.1.1.jar` e posteriormente execute a classe Main.java

## Documentação e decisões tomadas
Acesse a aba [wiki](link) para ter acesso a documentação e decisões tomadas (Inclui consulta com chat gpt).
