Todo API (mashape)
==================

Jersey implementation of RESTful todo API. See endpoints section to see what you can hit.

Also added a HTML frontend to interact with the api.

demo
----
See http://kenneth-todo.herokuapp.com


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
Depending on your webserver, copy `target/todo-webapp.war` to the deploy path.


rest resources
--------------

### Task
```json
{
	"id": "unique identifier",
	"title": "title of task",
	"body": "description of task",
	"order": 1,
	"done": true
}
```


endpoints
---------

For endpoint details, please see https://www.mashape.com/kennethkl/kenneth-todos


example requests
----------------

* GET /v1/tasks?q=test
  This will search for tasks that have test in title or body.

* GET /v1/tasks/54389645c026509e00123b1d
  This will return a single task.

