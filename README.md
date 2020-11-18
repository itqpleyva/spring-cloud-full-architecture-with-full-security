# spring-cloud-full-architecture-with-full-security

<h3>Full microservice cloud architecture using token and ACL based authorization</h3>

<img  src="img/full.png">

<h4>Authorization flow</h4>

<img  src="img/autho.png">

<h4>Tests guidlines</h4>

<p> import Test Collection.json into POSTMAN<p>
<p> Main endpoints<p>

      POST: http://localhost:8080/auth/authenticate

      BODY: 
      {
          "user":"user2",
          "password":"password"
      }

<hr>

    GET: http://localhost:8080/micro2/message
    
    IF token is valid and route is authorized
    
    RETURN: HELLO from microservice 2

<hr>

    GET: http://localhost:8080/micro1/message
    
    IF token is valid and route is authorized
    
    RETURN: HELLO from microservice 1
    
 <h4>ACL model</h4>
 
<img  src="img/aclmodel.png">
