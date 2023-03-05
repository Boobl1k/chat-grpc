Local DB in docker:  
`docker run -d --name chat-db -e POSTGRES_DB=testDb -e POSTGRES_USER=testUser -e POSTGRES_PASSWORD=testPassword -p 5432:5432 postgres:15.2-alpine`  
`docker start chat-db`

Local envoy in docker:  
`cd envoy`  
`docker build -t chat-envoy-image .`  
` docker run -d --name chat-envoy -p 8080:8080 -p 5000:5000 -p 9901:9901 chat-envoy-image`  
`docker start chat-envoy`

Local frontend without docker:  
`cd front`  
`npm run start`

Ports:
* 3000 - frontend
* 5000 - backend
* 9901 - envoy admin panel
* 8080 - envoy main