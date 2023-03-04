package ua.mk.essur.notesserviceproject.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Data
@Document("roles")
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    private String id;
    private String name;

    public Role(String name) {
        this.name = name;
    }


    @Override
    public String getAuthority() {
        return name;
    }
}
