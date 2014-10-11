Todo API (mashape)
==================

Jersey implementation of RESTful todo API. See endpoints section to see what you can hit.

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
	"done": true
}
```


endpoints
---------
All endpoints use JSON.

### GET /v1/tasks
Retrieve list of tasks.

#### Possible parameters:
`q` - search title and body for tasks

### POST /v1/tasks
Create a new task. The request body must be a task resource. The `id` field is ignored. Marking a task as done will send an SMS to Kenneth.

### GET /v1/tasks/{id}
Retrieve a single task identified by `id`.

### PUT /v1/tasks/{id}
Update a task identified by `id`. The request body must be a task resource. Marking a task as done will send an SMS to Kenneth.

### DELETE /v1/tasks/{id}
Deletes a task identified by `id`. Response will be blank when success (204).


example requests
----------------

* GET /v1/tasks?q=test
  This will search for tasks that have test in title or body.

* GET /v1/tasks/54389645c026509e00123b1d
  This will return a single task.

