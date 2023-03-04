package ua.mk.essur.notesserviceproject.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MongoPasswordEncoder {

    public static PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(5);
    }
}
