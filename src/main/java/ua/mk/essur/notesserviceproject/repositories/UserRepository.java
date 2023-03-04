package ua.mk.essur.notesserviceproject.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.mk.essur.notesserviceproject.model.MongoUser;

import java.util.Optional;

public interface UserRepository extends MongoRepository<MongoUser, String> {
    Optional<MongoUser> findByUsername(String username);
    boolean deleteMongoUserByUsername(String username);
}
