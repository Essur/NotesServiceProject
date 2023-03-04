package ua.mk.essur.notesserviceproject.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("notes")
public class Note {
    @Id
    private String id;
    private String content;
    private int likes;
    private LocalDateTime dateOfPublish;

    public Note(String content, int likes, LocalDateTime dateOfPublish) {
        this.content = content;
        this.likes = likes;
        this.dateOfPublish = dateOfPublish;
    }
    public void like(){
        likes+=1;
    }
    public void unlike(){
        if (likes != 0) {
            likes -= 1;
        } else throw new RuntimeException("Can`t unlike, because count of likes 0");
    }
}
