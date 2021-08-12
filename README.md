# VM BLOG
Projeto de APIs para um Blog

##APIs

APIs para gerenciamento de postagens.

####GET

_/v1/posts_

curl --location --request GET 'localhost:8080/?startDate=2021-01-01&endDate=2021-01-31'

####POST

_/v1/posts_

curl --location --request POST 'localhost:8080/' \
--header 'Content-Type: application/json' \
--data-raw '{
"title": 123414,
"description": "dfe",
"body": "ghi",
"createdAt": "2021-01-06",
"updatedAt": "2021-01-06"
}'

####PUT

_v1/posts/{postId}_

curl --location --request PUT 'localhost:8080/v1/posts/2' \
--header 'Content-Type: application/json' \
--data-raw '{
"title": "Iniciando em Java V2",
"description": "Curso para Javeiros.",
"body": "Curso 100% gratuito para programadores iniciantes em Java.",
"createdAt": "2021-01-06",
"updatedAt": "2021-01-06"
}'

####DELETE

_v1/posts/{postId}_

curl --location --request DELETE 'localhost:8080/v1/posts/1'