# Sprint05
 O projeto consiste em 3 apis, Uma API site, uma API para a criação de pedidos e outra API para consumir o pedido via mensageria usando o RabbitMQ
## API - Site
A api que permitir cadastrar itens, cliente e vincular cartão ao cliente. Além de enviar o pedido para a API pedido, através do endpoint Checkout.

### `Post:` ->  `http://localhost:8082/api/cliente` 

``` 
{
    "cpf": "CPF Válido",
    "nome" "Nome válido"
}

``` 
___
### `get:` ->  `http://localhost:8082/api/cliente` 
 Esse endpoint mostra todos os clientes
 
___
 ### `get:` ->  `http://localhost:8082/api/cliente/{cpf}` 
 Esse endpoint mostra o cliente específico.
 ___
 ### `put:` ->  `http://localhost:8082/api/cliente/{cpf}` 
 Esse endpoint permitir atualizar o nome do cliente especifico.
 ___

### `Post:` ->  `http://localhost:8082/api/item` 

``` 
{
    "nome": "Nome do item",
    "dataValidade": "28/02/2023 12:12:12 (data tem quer se futura)",
    "valor": 857.58 (valor positivo),
    "descricao": "descrição do item",
    "estoque": 50 (valor positivo)
}

``` 
___
### `get:` ->  `http://localhost:8082/api/item` 
 Esse endpoint mostra todos os item
___
 ### `get:` ->  `http://localhost:8082/api/item/{id}` 
 Esse endpoint mostra o item especifico.
 ___
 ### `put:` ->  `http://localhost:8082/api/item/{id}` 
 Esse endpoint permitir atualizar a data de validade, estoque e valor do item.
 ___

### `Post:` ->  `http://localhost:8082/api/cliente/{cpf}/cartoes` 

``` 
{
    "numero":"Numero do cartão valido com apenas digitos",
    "codigo":"Apenas os 3 digitos de segurança",
    "mesValidade":"Apenas um mes entre 1 - 12",
    "anoValidade":"Apenas um ano entre o atual e atual + 5",
    "marca":"Uma bandeira de cartão válida"
}

``` 
___
### `get:` ->  `http://localhost:8082/api/cliente/{cpf}/cartoes` 
 Esse endpoint mostra todos os cartoes
___
 ### `get:` ->  `http://localhost:8082/api/cliente/{cpf}/cartoes/{id}` 
 Esse endpoint mostra o cartao especifico.
 ___
 ### `put:` ->  `http://localhost:8082/api/cliente/{cpf}/cartoes/{id}` 
 Esse endpoint permitir atualizar os mesmo campos do post.
 ___

### `Post:` ->  `http://localhost:8082/api/checkout`

``` 
{
    "itens":[
        {
            "skuId": "SkuId unico do item",
            "qtd": "quantidade do item, deve ser positivo"
        },
        {
            "skuId": "ite0152641888151544454645671384815650344503",
            "qtd": 2
        }
    ],
    "cliente_info":{
        "clientId": "cpf do cliente",
        "cartaoId": "Id do cartao, vinculado"
    }
}

``` 
___
## API - Criação de pedidos
A api de criação de pedidos possuí os seguintes endpoints:

### `Post:` ->  `http://localhost:8080/api/pedido` 
Para a criação de um pedido é necessário informa o CPF **válido**,  uma lista de itens sendo obrigatório pelo menos 1 item, o tipo de pagamento dentro da opção (PIX, CREDIT_CARD, BANK_PAYMENT_SLIP), um objeto pagamento com os dados conforme o exemplo:
Um exemplo de pedido: 

``` 
{
    "cpf": "xxx.yyy.zzz-00",
    
    "itens": [],
    
    "tipoDoPagamento" : "CREDIT_CARD",
    
    "pagamento": {
        "numeroCartao": "xxxx xxxx xxxx",
        "nomeCartao": "yyyyyyyy",
        "codigoSeguranca": "000",
        "marca": "VISA",
        "mesExpiracao": "12",
        "anoExpiracao": "2022",
        "moeda": "BRL",
        "valor": 2258.59
    }
    
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

   ],
   "qtd": 2
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
            ],
            "qtd": 2
        },
        {
            "nome": "Celular",
            "descricao": "Aparelho Eletronico",
            "dataValidade": "27/10/2000 12:10:22",
            "valor": 1000
        }
        
    ],
    "tipoDoPagamento" : "CREDIT_CARD",
    "pagamento": {
        "numeroCartao": "xxxxxxxxxx",
        "nomeCartao": "yyyyyyyy",
        "codigoSeguranca": "000",
        "marca": "VISA",
        "mesExpiracao": 12,
        "anoExpiracao": 2022,
        "moeda": "BRL",
        "valor": 2258.59
    }
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
 
 
