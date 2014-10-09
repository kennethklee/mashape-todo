mashape-todo
============

Jersey implementation of RESTful todo API. See endpoints section to see what you can hit.

prerequisites
-------------
* Maven 2.x
* Java 1.7
* (Optional) Webserver such as Tomcat or jBoss


build
-----

1. Check out the project.
   ```bash
   git clone https://github.com/kennethklee/mashape-todo
   ```

2. Build the project.
   ```bash
   mvn clean install
   ```

deploy
------

### standalone
```bash
java -cp target/classes:target/dependency/* com.kenneth.todo.heroku.Main
```

### webserver
Depending on your webserver, copy `target/todo-webapp.war` to the 


endpoints
---------

### GET /v1/tasks
Retrieve list of task

### GET /v1/tasks/{id}
Retrieve a single task identified by `id`.

