jerseysrvrtest
=================

Basic *standalone* server implementing a RESTful books webservice using Jersey 1.x with JSON messaging.

### Build commands:
```
mvn clean
mvn package -Dmaven.test.skip=true
```

### Run using:
```
java -jar jarname.jar PORT_NUMBER
```
Example:
```
java -jar jerseysrvrtest-0.0.1-SNAPSHOT-jar-with-dependencies.jar 7001
```

### API Urls:
```
[GET] http://localhost:PORT_NUMBER/books
[GET] http://localhost:PORT_NUMBER/books/{id}
[POST] http://localhost:PORT_NUMBER/books
[PUT] http://localhost:PORT_NUMBER/books/{id}
[DELETE] http://localhost:PORT_NUMBER/books/{id}
```

#### Book JSON Object
```
{"id":3,"name":"Beginning Android Games"}
```

### References:
https://jersey.github.io/