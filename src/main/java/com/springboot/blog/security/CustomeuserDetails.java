package com.springboot.blog.security;

import com.springboot.blog.entity.Roles;
import com.springboot.blog.entity.Users;
import com.springboot.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomeuserDetails  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameoremail) throws UsernameNotFoundException {


        Users user = userRepository.findByUsernameOrEmail(usernameoremail,usernameoremail).orElseThrow(()->new UsernameNotFoundException("user not found"));
        return new User(user.getUsername(),user.getPassword(),maptoauthority(user.getRoles()));
    }


    private Collection<? extends GrantedAuthority> maptoauthority(Set<Roles> roles){
        return roles.stream().map((role)->new SimpleGrantedAuthority(role.getName()) ).collect(Collectors.toList());
    }

}
