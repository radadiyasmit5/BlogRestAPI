package com.springboot.blog.controller;

import com.springboot.blog.entity.Roles;
import com.springboot.blog.entity.Users;
import com.springboot.blog.payload.Jwtresponseentity;
import com.springboot.blog.payload.LoginDTO;
import com.springboot.blog.payload.SignupDTO;
import com.springboot.blog.repository.Rolerepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.utils.Jwttokengenrater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthConroller {

@Autowired
private AuthenticationManager authenticationManager;

@Autowired
private UserRepository userRepository;

@Autowired
private Rolerepository rolerepository;

@Autowired
private PasswordEncoder passwordEncoder;

@Autowired
private Jwttokengenrater tokenprovider;

@PostMapping("/signin")
   public ResponseEntity<Jwtresponseentity> signinAuthenticater(@RequestBody LoginDTO loginDTO){

    Authentication authentication= authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken( loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

    //get token from tokenprovider



    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = tokenprovider.generateToken(authentication);

    return  ResponseEntity.ok(new Jwtresponseentity(token));

}

@PostMapping("/signup")
public ResponseEntity<?> signUpauthenticator(@RequestBody SignupDTO signupDTO){

    if (userRepository.existsByEmail(signupDTO.getEmail())){
        return new ResponseEntity("Email is Already taken", HttpStatus.BAD_REQUEST);
    }

    if(userRepository.existsByUsername(signupDTO.getUsername())){
        return new ResponseEntity<>("username is already taken",HttpStatus.BAD_REQUEST);
    }


   Users user= new Users();
   user.setName(signupDTO.getName());
   user.setUsername(signupDTO.getUsername());
   user.setEmail(signupDTO.getEmail());
   user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

   Roles roles= rolerepository.findByName("ROLE_USER").get();

   user.setRoles(Collections.singleton(roles));

    userRepository.save(user);


return new ResponseEntity<>("User Register successfully",HttpStatus.OK);

}
}
