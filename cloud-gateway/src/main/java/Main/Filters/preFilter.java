package Main.Filters;




import java.util.List;
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
Boolean flag;
String role ;

  @Override
  public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) {

      final ServerHttpRequest request = exchange.getRequest();
      final String path = request.getPath().toString();
      final String authenticate_path = "/auth/authenticate";

      if (path.equals(authenticate_path)) {// si viene de authentication sigue de largo

          return chain.filter(exchange);
      } else {
          if (request.getHeaders().containsKey("Authorization")) {// preguntando por la cabecera y el token

              final String autho_headers = request.getHeaders().get("Authorization").get(0);
              final String token = autho_headers.replace("Bearer ", "");
              final TokenValidrequest tokenRequest = new TokenValidrequest(token, "user2");

              final HttpHeaders headers = new HttpHeaders(); // preparando la peticion con resttemplate
              final RestTemplate restTemplate = new RestTemplate();
              headers.setContentType(MediaType.APPLICATION_JSON);
              headers.add("Accept", "application/json");
              headers.add("Authorization", "Bearer " + token);
              final HttpEntity<TokenValidrequest> request1 = new HttpEntity<>(tokenRequest, headers);
              final String fooResourceUrl = "http://localhost:8085/auth/valid_token";

              try {
                  final ResponseEntity<List> response = restTemplate.postForEntity(fooResourceUrl, request1,
                          List.class);

                  flag = Boolean.parseBoolean((String) response.getBody().get(0));// guardando el boolean del si el
                                                                                  // token es valido

                  role = (String) response.getBody().get(1);// guardando el rol del usuario

              } catch (final Exception e) {

                  return this.onError(exchange, "Token invalido", HttpStatus.UNAUTHORIZED);
              }

              if (!flag) {// llamar metodo token validation de auth

                  return this.onError(exchange, "Token invalido", HttpStatus.UNAUTHORIZED);
              } else {

                  return chain.filter(exchange);
              }
          } else {
              return this.onError(exchange, "Token invalido", HttpStatus.UNAUTHORIZED);
          }
      }
  }

  private Mono<Void> onError(final ServerWebExchange exchange, final String err, final HttpStatus httpStatus) {

      final ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(httpStatus);

    return response.setComplete();
}

}