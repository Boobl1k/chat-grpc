# Startup instructions

### Run everything in docker:  
`docker-compose up`

### Local frontend without docker:  
see `front/README.md`

### Local api without docker:  
for now you need to change hostname in appDbContext 
(sorry about that 😊 should be configured in [#5](https://github.com/Boobl1k/chat-grpc/issues/5))

# Ports:

* 3000 - frontend
* 5000 - backend
* 9901 - envoy admin panel
* 8080 - envoy main
* 5432 - DB



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