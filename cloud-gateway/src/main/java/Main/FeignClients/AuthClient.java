package Main.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import Main.Models.TokenValidrequest;

@FeignClient(name = "authentication-micro")
@RequestMapping("/auth")
public interface AuthClient {

    
    @PostMapping(value="/valid_token")
    public Boolean isValidToken(@RequestBody TokenValidrequest token_request);
}