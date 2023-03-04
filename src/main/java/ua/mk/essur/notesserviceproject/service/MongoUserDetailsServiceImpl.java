package ua.mk.essur.notesserviceproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.mk.essur.notesserviceproject.encoder.MongoPasswordEncoder;
import ua.mk.essur.notesserviceproject.model.MongoUser;
import ua.mk.essur.notesserviceproject.model.Role;
import ua.mk.essur.notesserviceproject.repositories.UserRepository;

import java.util.*;

@Slf4j
@Service("mongoUserDetailsServiceImpl")
public class MongoUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = MongoPasswordEncoder.getPasswordEncoder();


    public MongoUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MongoUser mongoUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username : " + username + " not found"));
        Set<GrantedAuthority> authorities = new HashSet<>();
        mongoUser.getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getAuthority())));
        return new User(mongoUser.getUsername(), mongoUser.getPassword(), authorities);
    }

    public boolean saveMongoUser(String username, String password){
        Optional<MongoUser> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return false;
        } else {
            MongoUser mongoUser = new MongoUser();
            mongoUser.setUsername(username);
            mongoUser.setPassword(passwordEncoder.encode(password));
            mongoUser.setRoles(Collections.singleton(new Role("USER")));
            userRepository.save(mongoUser);
            return true;
        }
    }

    public List<MongoUser> findALlUsers(){
        return userRepository.findAll();
    }

    public boolean deleteUserByUsername(String username){
        long result = userRepository.deleteMongoUserByUsername(username);
        return result == 1;
    }

}
