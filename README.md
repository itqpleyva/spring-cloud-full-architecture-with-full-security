# spring-cloud-full-architecture-with-full-security

<h3>Full microservice cloud architecture using token and ACL based authorization</h3>

<img  src="img/full.png">

<h4>Authorization flow</h4>

<img  src="img/autho.png">

<h4>Tests guidlines</h4>

<p> import Test Collection.json into POSTMAN<p>
      
<h5> Main endpoints</h5>

      POST: http://localhost:8080/auth/authenticate

      BODY: 
      {
          "user":"user2",
          "password":"password"
      }

<hr>

    GET: http://localhost:8080/micro2/message
    
    must have AUTHORIZATION HEADER = Bearer token
    
    IF token is valid and route is authorized
    
    RETURN: HELLO from microservice 2

<hr>

    GET: http://localhost:8080/micro1/message
    
    must have AUTHORIZATION HEADER = Bearer token
    
    IF token is valid and route is authorized
    
    RETURN: HELLO from microservice 1
    
 <h4>ACL model</h4>
 
<img  src="img/aclmodel.png">

<strong>allowed_roles</strong> is a String variable
      
      
 <h4>Main Method to control token validation and authorization:</h4>
 
      @PostMapping(value = "/valid_token")
      public Boolean isValidRequest(@RequestBody final ValidationRequest validation_request) throws Exception {

        boolean isvalid = true;// variable para dar autorizacion
        boolean isValidToken = false;// variable para token valido o no
        boolean isAuthoPath = false;//variable para ruta autorizada o no

        JPQLQuery<?> query = new JPAQuery<>(entityManager);

        final MyUserDetails userDetails1 = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();       
        final List<?> list_roles = new ArrayList<>(userDetails1.getAuthorities());      

        try {

            isValidToken = jwt.validateToken(validation_request.getToken(), userDetails1);

         QACLModel aclModel = QACLModel.aCLModel;
         String r = list_roles.get(0).toString();
         List<ACLModel> acl_list =                      query.select(aclModel).from(aclModel).where(aclModel.method.like(validation_request.getMethod())).where(aclModel.allowed_roles.like("%"+r+"%")).fetch();
         PathPatternParser pathPattern = new PathPatternParser();
         pathPattern.setCaseSensitive(false);

            if (acl_list.size()== 0) {
               
                isAuthoPath = false;

            }
            else{
                
                Optional<ACLModel> acl= acl_list.stream().filter(u ->{
                    PathPattern p = pathPattern.parse(u.getPath());
                    return p.matches(PathContainer.parsePath(validation_request.getPath()));
                } ).findFirst();

                if (acl.isPresent()) {

                    isAuthoPath = true;
                }
                else{

                    isAuthoPath = false;
                }               
            }           

        } catch (final Exception e) {

            isvalid = false;
        }

            isvalid = isValidToken == true && isAuthoPath == true ? true : false;
            return isvalid;           
    }
