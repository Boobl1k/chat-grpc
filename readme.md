Local DB in docker:  
`docker run -d --name chat-db -e POSTGRES_DB=testDb -e POSTGRES_USER=testUser -e POSTGRES_PASSWORD=testPassword -p 5432:5432 postgres:15.2-alpine`