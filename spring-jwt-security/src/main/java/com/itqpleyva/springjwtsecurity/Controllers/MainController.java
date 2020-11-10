package com.itqpleyva.springjwtsecurity.Controllers;

import com.itqpleyva.springjwtsecurity.JwtManagement.Jwt;
import com.itqpleyva.springjwtsecurity.Models.AuthenticationRequest;
import com.itqpleyva.springjwtsecurity.Models.AuthenticationResponse;
import com.itqpleyva.springjwtsecurity.Models.TokenValidrequest;
import com.itqpleyva.springjwtsecurity.Services.MyUserDetails;
import com.itqpleyva.springjwtsecurity.Services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/auth")
public class MainController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myuserdatilService;


    @Autowired
    private Jwt jwt;
    
    @RequestMapping("/home")
    public String home() {

        MyUserDetails userDetails  = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
                
        return "Hello " + userDetails.getUsername() + " from MainController";
    }

    @RequestMapping(value="/authenticate", method=RequestMethod.POST)
    public ResponseEntity<?> requestMethodName( @RequestBody AuthenticationRequest authrequest) throws Exception {

   
        try {
            authenticationManager.authenticate( 
            
                new UsernamePasswordAuthenticationToken(authrequest.getUser(), authrequest.getPassword())
                
                );
        } catch (BadCredentialsException e) {
            
            throw new Exception("username or password incorrect");
        }

        final UserDetails userDetails = myuserdatilService.loadUserByUsername(authrequest.getUser());
        final String token = jwt.generateToken(userDetails);

        return new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);
    }
    
    @PostMapping(value="/valid_token")
    public Boolean isValidToken(@RequestBody TokenValidrequest token_request )throws Exception {

        boolean isvalid = false;

        UserDetails user = myuserdatilService.loadUserByUsername(token_request.getUsername());

            try {
               isvalid = jwt.validateToken(token_request.getToken(), user);

            } catch (Exception e) {

                return isvalid;
            }
             return isvalid;
    }
    
}