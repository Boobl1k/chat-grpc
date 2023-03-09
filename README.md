# Startup instructions

### Local DB in docker:  
`docker run -d --name chat-db -e POSTGRES_DB=testDb -e POSTGRES_USER=testUser -e POSTGRES_PASSWORD=testPassword -p 5432:5432 postgres:15.2-alpine`  
`docker start chat-db`

### Local envoy in docker:  
`cd envoy`  
`docker build -t chat-envoy-image .`  
`docker run -d --name chat-envoy -p 8080:8080 -p 5000:5000 -p 9901:9901 chat-envoy-image`  
`docker start chat-envoy`

### Local frontend without docker:  
see `front/README.md`



# Ports:

* 3000 - frontend
* 5000 - backend
* 9901 - envoy admin panel
* 8080 - envoy main



# Review rules

* reviewer opens thread about every problem they see in the code
* performer comments each open thread in each review cycle
* if reviewer agreed with new code changes 
(or any other reason why they are convinced that the problem is gone), 
they resolve the thread
* if reviewer still does not agree with changes, 
they have to comment the thread and give more explanations about the problem
* in order to solve the problem performer opens a new issue and refers
to it in comment or makes changes in current PR 
(and also comments about new changes under the thread).
The second way is better