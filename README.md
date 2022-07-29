# Sprint05
 O projeto consiste em 2 apis, uma API para a criação de pedidos e outra API para consumir o pedido via mensageria usando o RabbitMQ
## API - Criação de pedidos
A api de criação de pedidos possuí os seguintes endpoints:

### `Post:` ->  `http://localhost:8080/api/pedido` 
Para a criação de um pedido é necessário informa o CPF **válido** e uma lista de itens sendo obrigatório pelo menos 1 item.
Um exemplo de pedido: 

``` 
{
    "cpf": "xxx.yyy.zzz-00",
    
    "itens": []
    
}
```
O item deve seguir o modelo abaixo, destacando que a oferta é uma **lista de ofertas** e **opcional**; Além disso a `dataValidade` da **oferta** deve ser uma data e hora posterior ao do criação (o sistema cria automáticamente)
```
{
   "nome": "Celular",
   "descricao": "Aparelho Eletronico",
   "dataValidade": "27/10/2000 12:10:22",
   "valor": 1258.59,
   "ofertas": [
       {
           "nome": "imperdivel",
           "descricao": "Oferta unica",
           "desconto" : 50,
           "dataValidade": "25/10/2023 10:10:10"
       }

   ]
}
```
Exemplo de forma completa: 
```
{
    "cpf": "xxx-yyy-zzz-00",
    "itens": [
        {
            "nome": "Celular",
            "descricao": "Aparelho Eletronico",
            "dataValidade": "27/10/2000 12:10:22",
            "valor": 1258.59,
            "ofertas": [
                {
                    "nome": "imperdivel",
                    "descricao": "Oferta unica",
                    "desconto" : 50,
                    "dataValidade": "25/10/2023 10:10:10"
                },
                {
                    "nome": "imperdivel",
                    "descricao": "Oferta unica",
                    "desconto" : 50,
                    "dataValidade": "20/10/2023 10:10:10"
                }
            ]
        },
        {
            "nome": "Celular",
            "descricao": "Aparelho Eletronico",
            "dataValidade": "27/10/2000 12:10:22",
            "valor": 1000
        }
        
    ]
}
```
 ___
 ### `get:` ->  `http://localhost:8080/api/pedido` 
 Esse endpoint mostra todos os pedidos, ele tem como parametros: 
 
 `cpf` que serve como filtro para mostrar todos pedidos com aquele cpf
 
 Ex: `http://localhost:8080/api/pedido?cpf=111.111.111-01`
 
 `sort` que serve para order a busca, podendo ordenar pelo valor total:
 
 De forma **ascedente**
 
 Ex: `http://localhost:8080/api/pedido?sort=total,asc`
 
 De forma **descendente**
 
 Ex: `http://localhost:8080/api/pedido?sort=total,desc`
 ___
 ### `get:` ->  `http://localhost:8080/api/pedido/{id}` 
 esse endpoint mostra o pedido de forma unitária
 ___
 ### `delete:` ->  `http://localhost:8080/api/pedido/{id}` 
 Esse endpoint deleta o pedido, com o id informado
 ___
 ### `patch:` ->  `http://localhost:8080/api/pedido/{id}` 
 Esse endpoint permitir atualizar os mesmo campos do `post`, campos **não informados** ou em **branco** serão desconsiderados
 __
 ### `patch:` ->  `http://localhost:8080/api/item/{id}` 
 Esse endpoint permitir atualizar os mesmo campos do `post`, campos **não informados** ou em **branco** serão desconsiderados
 ___
 
 ## API - Pagamento
 Ao ser feito um post com sucesso na [API PEDIDO](https://github.com/PedroHAlvesS/Mercado/blob/main/README.md#api---cria%C3%A7%C3%A3o-de-pedidos), será enviado via mensangeria (RabbitMQ) uma mensagem para esse API com os dados do **pedido** `id` e `total` , no qual será salvo em uma outra tabela no banco de dados
 
 ## Requisitos:
 Spring Boot
 
 Java v11
 
 RabbitMQ
 
 MySql
 
 
