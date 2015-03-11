# spike
Integrate scalatest, springboot, gradle to build test

# Enviornment

- gradle
- jdk

# Build
gradle build

# test
gradle test

# How to Use

- Create a DataBase: http://localhost:8080/create?json={%20%22name%22:%20%22user%22,%20%22data%22:%20[%20{%20%22name%22:%20%22rose%22,%20%22age%22:%2018%20}%20]%20}
- Get: http://localhost:8080/data/user/0
- Post: http://localhost:8080/data/user/ 

  data: { "name" : "tina", "age" : 18}

- Delete: http://localhost:8080/data/user/0
- Post(the spring boot not support the `PUT` method): http://localhost:8080/data/user/0

  data: {"name": "jack"}

## Swagger-ui Api docs:
Visit: [http://localhost:8080/index.html](http://localhost:8080/index.html)

