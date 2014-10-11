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
java -cp target/classes:target/dependency/* com.kenneth.todo.Main
```

### webserver
Depending on your webserver, copy `target/todo-webapp.war` to the deploy path.


configurations
--------------
There are configurations located in `/src/main/resources/application.properties`

* data.storage
  Specified what kind of storage to use. Options are: `mongo` and `memory`.
  `mongo` uses the `mongo.uri` and `mongo.name` configurations below to connect to a MongoDB.
  `memory` users an in-memory database.

* elasticsearch.url
  Url to connect to an elastic search server.

* twilio.sid & twilio.token
  Twilio account sid and authentication token to send SMS messages.

* mongo.uri
  When `data.storage` is set to `mongo`, this is used to connect to the mongo db. This configuration looks like: `mongodb://<username>:<password>@host:port/db-name`
* mongo.name
  The name of the database to use. The username/password must have access to that database.

* sms.enable
  Toggle of whether to send SMS messages or not.

* sms.from
  SMS messages will have this number in the FROM field.

* sms.to
  SMS messages will be delivered to this number.


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

