package com.springboot.blog.security;

import com.springboot.blog.utils.Jwttokengenrater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Jwtauthenticationfilter extends OncePerRequestFilter {


    @Autowired
    Jwttokengenrater tokenprovider;

    @Autowired
    CustomeuserDetails customeuserDetails;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //geting token from request
        String jwttoken = getAuthenticationtoken(request);


        if(StringUtils.hasText(jwttoken)&&tokenprovider.valideToken(jwttoken)){

            String username = tokenprovider.getusernamefromtoken(jwttoken);

            UserDetails userDetails = customeuserDetails.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,null,userDetails.getAuthorities()
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);



        }
        filterChain.doFilter(request,response);



    }

    private String getAuthenticationtoken(HttpServletRequest request){

        String bearertoken   =  request.getHeader("Authorization");
    if(StringUtils.hasText(bearertoken)&& bearertoken.startsWith("Bearer ")){
               return  bearertoken.substring(7,bearertoken.length());
    }
    else {
        return null;
    }
    }
}
