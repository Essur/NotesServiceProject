package ua.mk.essur.notesserviceproject.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.mk.essur.notesserviceproject.model.Note;
import ua.mk.essur.notesserviceproject.repositories.NotesRepository;

import javax.annotation.security.RolesAllowed;
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
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<List<Note>> findAllNotes(){
        List<Note> notes = notesRepository.findAll();
        return ResponseEntity.ok(notes);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNote(@RequestBody String text){
        Note note = new Note(text, 0, LocalDateTime.now());
        notesRepository.save(note);
        return ResponseEntity.ok("Successfully added");
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteNote(@RequestBody String id){
        notesRepository.deleteById(id);
        return ResponseEntity.ok("Note with id " + id + " successfully deleted");
    }

    @PostMapping("/updateNote")
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
    public ResponseEntity<List<Note>> sortNotesByDataAndFindAll(){
        List<Note> notes = notesRepository.findAllByOrderByDateOfPublishDesc();
        return ResponseEntity.ok(notes);
    }

    @PostMapping("/like")
    public ResponseEntity<String> likeNote(@RequestParam("id") String id){
        Optional<Note> note = notesRepository.findById(id);
        if (note.isPresent()) {
            note.get().like();
            notesRepository.save(note.get());
            return ResponseEntity.ok("Note with id " + id + " successfully liked");
        } else return ResponseEntity.ok("Note with id " + id + " not found");
    }

    @PostMapping("/unlike")
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
