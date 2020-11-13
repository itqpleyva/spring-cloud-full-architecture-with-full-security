package com.itqpleyva.springjwtsecurity.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
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

        final MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return "Hello " + userDetails.getUsername() + " from MainController";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> requestMethodName(@RequestBody final AuthenticationRequest authrequest) throws Exception {

        try {
            authenticationManager.authenticate(

                    new UsernamePasswordAuthenticationToken(authrequest.getUser(), authrequest.getPassword())

            );
        } catch (final BadCredentialsException e) {

            throw new Exception("username or password incorrect");
        }

        final UserDetails userDetails = myuserdatilService.loadUserByUsername(authrequest.getUser());
        final String token = jwt.generateToken(userDetails);

        return new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);
    }

    @PostMapping(value = "/valid_token")
    public List<?> isValidToken(@RequestBody final TokenValidrequest token_request) throws Exception {

        List<String> result_list = new ArrayList<>();

        boolean isvalid = false;

        final UserDetails user = myuserdatilService.loadUserByUsername(token_request.getUsername());

        final List<?> list_roles = new ArrayList<>(user.getAuthorities());      

        try {

            isvalid = jwt.validateToken(token_request.getToken(), user);

        } catch (final Exception e) {

            isvalid = false;
        }

            result_list.add(String.valueOf(isvalid));
            result_list.add(String.valueOf(list_roles.get(0)));

            return result_list;
    }
    
}