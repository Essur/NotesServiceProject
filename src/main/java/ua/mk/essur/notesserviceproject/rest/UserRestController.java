package ua.mk.essur.notesserviceproject.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.mk.essur.notesserviceproject.model.MongoUser;
import ua.mk.essur.notesserviceproject.service.MongoUserDetailsServiceImpl;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserRestController {
    private MongoUserDetailsServiceImpl mongoUserDetailsService;

    public UserRestController(MongoUserDetailsServiceImpl mongoUserDetailsService) {
        this.mongoUserDetailsService = mongoUserDetailsService;
    }


    @GetMapping("/showAll")
    public ResponseEntity<List<MongoUser>> testRequest(){
        return ResponseEntity.ok(mongoUserDetailsService.findALlUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestParam("username") String username,
                                          @RequestParam("password") String password){
        boolean result = mongoUserDetailsService.saveMongoUser(username, password);
        if (result){
            return ResponseEntity.ok("User with username : " + username + " successfully added");
        } else return ResponseEntity.ok("User with username : " + username + " already exist");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserByUsername(@RequestParam("username") String username){
        boolean result = mongoUserDetailsService.deleteUserByUsername(username);
        if (result){
            return ResponseEntity.ok("User with username : " + username + " successfully deleted");
        } else return ResponseEntity.ok("User with username : " + username + " not found");
    }
}
