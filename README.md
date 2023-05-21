# Startup instructions

### Run everything in docker:  
`docker compose up`

### Local frontend without docker:  
see `front/README.md`

### Local api without docker:  
you need to change hostname in appDbContext

# Ports:

* 3000 - frontend
* 5000 - backend
* 9901 - envoy admin panel
* 8080 - envoy main
* 5432 - DB
* 5100 - mybook api



# Auth

accounts are common for mybook and chat, but auth itself is separated  
so in order to authenticate first you need to register in myBook http://localhost:5100/swagger/index.html  
then use same login and pass here http://localhost:3000/



# [Figma](https://www.figma.com/file/Jj5nXUF5uwNzeBW6aiURVK/chat-grpc?node-id=0%3A1&t=35oIPGSwOlcMAtdp-1)
