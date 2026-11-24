# Baozi Store

API REST para cadastrar clientes, produtos e pedidos de uma loja de pães chineses.

## Executar o código-fonte

Com JDK 17 ou superior e Maven 3.6.3 ou superior, abra um terminal na pasta que contém o pom.xml e execute:

    mvn spring-boot:run

Para gerar o executável, use mvn clean package. O arquivo target/baozi-store-1.0.0.jar inicia com java -jar. O banco H2 é criado automaticamente na pasta dados, relativa à pasta de execução.

## Recursos

A URL base é http://localhost:8080/api. Para clientes, produtos e pedidos, use POST e GET no caminho da coleção e GET e DELETE no caminho com /{id}. O POST de pedido recebe clienteId, produtoId e quantidade. Exclua o pedido antes de excluir os cadastros relacionados.

## Organização

- model mapeia as entidades e suas relações para as tabelas.
- repository fornece operações de persistência por meio de JpaRepository.
- controller recebe requisições, valida dados e define as respostas HTTP.
