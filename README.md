# Hello-world project

This is a hello world Java / gradle / Spring Boot (version 1.5.9) application.

## How to Run 

This application is packaged as a jar which has Tomcat embedded. You run it using the ```java -jar``` command.

* Clone this repository 
* Make sure you are using JDK 1.8 and gardle 4.x
* You can build the project and run the tests by running ```gradle clean build```
* Once successfully built, you can run the service by one of these two methods:
```
        java -jar -Dspring.profiles.active=test build/libs/hello-world-0.1.0.jar
or
        gradle clean assemble bootRun
```
* Check the stdout or /var/log/hello-world.log file to make sure no exceptions are thrown

Once the application runs you should see something like this

```
""2018-01-17 14:32:03 [main] INFO  com.fun.coding.Application - Started Application in 3.796 seconds (JVM running for 4.067)""
```

## About the Service

The service is just a simple hello world REST service. It uses an in-memory database (HSQL) to store the data. You can call some REST endpoints defined in ```com.fun.coding.rest.*``` on **port 8080**. (see below)

For health check you can call ```/health``` (available on **port 8080**)

Here is what this hello world application demonstrates: 

* Full integration with the **Spring** Framework.
* Packaging as a single jar with embedded container (tomcat): No need to install a container separately on the host just run using the ``java -jar`` command
* Writing a RESTful service using annotation: supports JSON request / response
* Demonstrates Mockito and MockMVC test framework with associated libraries
* All APIs are documented below

Here are some endpoints you can call:

### health check.

```
http://localhost:8080/health

curl http://localhost:8080/health

RESPONSE
{"status":"UP"}
```

### Retrieve N Fibonacci numbers

* REST endpoint that accepts a number, N, and returns a JSON array with the first N Fibonacci numbers.

```
GET /fibonacci/[number]

curl http://localhost:8080/fibonacci/5

REQUEST:
/fibonacci/5

RESPONSE: HTTP 200 (OK)
{
    "number": 5,
    "seriesList": [
        0,
        1,
        1,
        2,
        3
    ]
}

```

### Words occurrences

* REST endpoint that accepts a JSON object containing a paragraph of text and returns a JSON array of objects.
* The returned objects represent the unique words from the supplied paragraph along with a count of the number of occurrences. 
* The array of objects sorted alphabetically.

```
POST /words/occurrences
Accept: application/json
Content-Type: application/json

curl -H "Content-Type: application/json" -X POST -d '{"content": "yes a no foo a foo ta yes no"}' http://localhost:8080/words/occurrences

REQUEST: {"content": "yes a no foo a foo ta yes no"}

RESPONSE: HTTP 200 (OK)

[
    {
        "word": "a",
        "count": 2
    },
    {
        "word": "foo",
        "count": 2
    },
    {
        "word": "no",
        "count": 2
    },
    {
        "word": "ta",
        "count": 1
    },
    {
        "word": "yes",
        "count": 2
    }
]

```

### Monitor deadLock

* REST endpoint that creates two threads that become deadlocked with each other
* Monitor the two threads and detect the deadlock
* Logging the details and gracefully shutdown the service, in case a deadlock occurred. 

```
GET /monitor

curl http://localhost:8080/monitor

RESPONSE: HTTP 200 (OK)

Monitoring...!

```

### REST endpoints that add, query, and delete rows in a database

* Create Book

```
POST /books
Accept: application/json
Content-Type: application/json

curl -H "Content-Type: application/json" -X POST -d '{ "isbn": 123,"title": "title","author": "author"}' http://localhost:8080/books

REQUEST:
{
    "isbn": 123,
    "title": "title",
    "author": "author"
}

RESPONSE: HTTP 201 (CREATED)

{
    "isbn": 123,
    "title": "java book",
    "author": "nizar"
}
```

* Get Book

```
GET /books/[isbn]

curl http://localhost:8080/books/123

REQUEST:
/books/123

RESPONSE: HTTP 200 (OK)

{
    "isbn": 123,
    "title": "java book",
    "author": "nizar"
}
```

* Delete Book

```
DELETE /books/[isbn]

curl  -X DELETE  http://localhost:8080/books/123

REQUEST:
/books/123

RESPONSE: HTTP 200 (OK)

{"isbn": 123}

```

### Retrieve list of users from external service
* REST endpoint that queries an external REST service using Spring RestTemplate.

```
GET /users

curl http://localhost:8080/users

Response: HTTP 200
Content: JSON object
[
    {
        "userId": 1,
        "id": 1,
        "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
        "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
    },
    {
        "userId": 1,
        "id": 2,
        "title": "qui est esse",
        "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
    }
]    
```

# Comments: alseddnm@gmail.com