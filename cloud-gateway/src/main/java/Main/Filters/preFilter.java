package Main.Filters;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import Main.FeignClients.AuthClient;
import Main.Models.TokenValidrequest;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Mono;

@Component
public class preFilter implements GlobalFilter {

AuthClient authClient;
Boolean flag = false;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    
    ServerHttpRequest request = exchange.getRequest();
    String path = request.getPath().toString();
    String authenticate_path = "/auth/authenticate";
    String isvalid_path = "/auth/valid_token";

    if (path.equals(authenticate_path)) {// si viene de authentication sigue de largo
        
        return chain.filter(exchange);
    }
    else{
             if (request.getHeaders().containsKey("Authorization")) {// preguntando por la cabecera y el token

                String autho_headers= request.getHeaders().get("Authorization").get(0);
                String token = autho_headers.replace("Bearer ", "");
                System.out.println(token);
                TokenValidrequest tokenRequest = new TokenValidrequest(token, "user2");

                HttpHeaders headers = new HttpHeaders(); //preparando la peticion con resttemplate
                RestTemplate restTemplate = new RestTemplate();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("Accept", "application/json");
                headers.add("Authorization", "Bearer " + token);
                HttpEntity<TokenValidrequest> request1 = new HttpEntity<>(tokenRequest,headers);
                String fooResourceUrl = "http://localhost:8085/auth/valid_token";

                try {
                    ResponseEntity<String> response = restTemplate.postForEntity(fooResourceUrl ,request1, String.class);
                    flag = Boolean.parseBoolean(response.getBody());
                    
                } catch (Exception e) {

                    return this.onError(exchange, "Token invalido", HttpStatus.UNAUTHORIZED);
                }
                
                
                if (!flag ) {//llamar metodo token validation de auth
                       
                    return this.onError(exchange, "Token invalido", HttpStatus.UNAUTHORIZED);
               }
                else{
                   
                    return chain.filter(exchange);              
                }
            }
            else{
                return this.onError(exchange, "Token invalido", HttpStatus.UNAUTHORIZED);
            } 
    }
  }
  
  private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus)  {
   
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(httpStatus);

    return response.setComplete();
}

}