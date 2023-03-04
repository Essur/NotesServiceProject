package ua.mk.essur.notesserviceproject.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.mk.essur.notesserviceproject.model.Note;

import java.util.List;

public interface NotesRepository extends MongoRepository<Note, String> {
    List<Note> findAllByOrderByDateOfPublishDesc();
}
