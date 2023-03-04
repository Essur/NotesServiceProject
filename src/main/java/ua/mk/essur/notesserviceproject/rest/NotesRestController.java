package ua.mk.essur.notesserviceproject.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ua.mk.essur.notesserviceproject.model.Note;
import ua.mk.essur.notesserviceproject.repositories.NotesRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/notes")
public class NotesRestController {
    private final NotesRepository notesRepository;

    public NotesRestController(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @GetMapping
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public ResponseEntity<List<Note>> findAllNotes(){
        List<Note> notes = notesRepository.findAll();
        return ResponseEntity.ok(notes);
    }

    @PostMapping("/add")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public ResponseEntity<String> addNote(@RequestBody String text){
        Note note = new Note(text, 0, LocalDateTime.now());
        notesRepository.save(note);
        return ResponseEntity.ok("Successfully added");
    }

    @DeleteMapping("/deleteById")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public ResponseEntity<String> deleteNote(@RequestBody String id){
        notesRepository.deleteById(id);
        return ResponseEntity.ok("Note with id " + id + " successfully deleted");
    }

    @PostMapping("/updateNote")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public ResponseEntity<String> updateNote(@RequestParam("id") String id,
                                             @RequestParam("text") String text){
        Optional<Note> note = notesRepository.findById(id);
        if (note.isPresent()) {
            note.get().setContent(text);
            notesRepository.save(note.get());
            return ResponseEntity.ok("Note with id " + id + " successfully updated");
        } else return ResponseEntity.ok("Note with id " + id + " not found");
    }

    @GetMapping("/sortByDate")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public ResponseEntity<List<Note>> sortNotesByDataAndFindAll(){
        List<Note> notes = notesRepository.findAllByOrderByDateOfPublishDesc();
        return ResponseEntity.ok(notes);
    }

    @PostMapping("/like")
    @Secured("ROLE_USER")
    public ResponseEntity<String> likeNote(@RequestParam("id") String id){
        Optional<Note> note = notesRepository.findById(id);
        if (note.isPresent()) {
            note.get().like();
            notesRepository.save(note.get());
            return ResponseEntity.ok("Note with id " + id + " successfully liked");
        } else return ResponseEntity.ok("Note with id " + id + " not found");
    }

    @PostMapping("/unlike")
    @Secured("ROLE_USER")
    public ResponseEntity<String> unlikeNote(@RequestParam("id") String id){
        Optional<Note> note = notesRepository.findById(id);
        if (note.isPresent()) {
            try {
                note.get().unlike();
                notesRepository.save(note.get());
                return ResponseEntity.ok("Note with id " + id + " successfully unliked");
            } catch (RuntimeException e){
                return ResponseEntity.ok("Note with id " + id + " can`t be unliked, because count of likes 0");
            }
        } else return ResponseEntity.ok("Note with id " + id + " not found");
    }
}
