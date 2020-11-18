package com.itqpleyva.springjwtsecurity.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itqpleyva.springjwtsecurity.JwtManagement.Jwt;
import com.itqpleyva.springjwtsecurity.Models.ACLModel;
import com.itqpleyva.springjwtsecurity.Models.AuthenticationRequest;
import com.itqpleyva.springjwtsecurity.Models.AuthenticationResponse;
import com.itqpleyva.springjwtsecurity.Models.ValidationRequest;
import com.itqpleyva.springjwtsecurity.Repositories.ACLRepository;
import com.itqpleyva.springjwtsecurity.Services.MyUserDetails;
import com.itqpleyva.springjwtsecurity.Services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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

    @Autowired
    ACLRepository aclRepository;

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
    public Boolean isValidRequest(@RequestBody final ValidationRequest validation_request) throws Exception {

        boolean isvalid = true;// variable para dar autorizacion
        boolean isValidToken = false;// variable para token valido o no
        boolean isAuthoPath = false;//variable para ruta autorizada o no

        final MyUserDetails userDetails1 = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();       
        final List<?> list_roles = new ArrayList<>(userDetails1.getAuthorities());      

        try {

            isValidToken = jwt.validateToken(validation_request.getToken(), userDetails1);
        //    isAuthoPath = routeByRoles.get(validation_request.getPath()).contains(list_roles.get(0).toString());
            ACLModel acl_match = new ACLModel(validation_request.getPath(), validation_request.getMethod(), null);
            Example<ACLModel> acl_example = Example.of(acl_match);
            List<ACLModel> acl_list = aclRepository.findAll(acl_example);

            if (acl_list.size()== 0) {
               
                isAuthoPath = false;

            }else if(!acl_list.get(0).getAllowed_roles().contains(list_roles.get(0).toString())){

                isAuthoPath = false;
            }
            else{
                
                isAuthoPath = true;
            }
            

        } catch (final Exception e) {

            isvalid = false;
        }

            isvalid = isValidToken == true && isAuthoPath == true ? true : false;
            return isvalid;           
    }
    
}