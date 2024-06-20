package portfolioserver.core.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolioserver.common.BaseController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserController extends BaseController {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserController(UserRepository userRepository) {
        super(); // Enables access to logger.
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/users/get")
    public List<User> get() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/user/{id}/get")
    public ResponseEntity<User> getOne(@PathVariable int id) {
        return ResponseEntity.ok(
            userRepository.findById((long) id)
                .orElseThrow(() -> new NoSuchElementException(User.class.getSimpleName()))
        );
    }

    // POST will create a child resource.
    @PostMapping(value = "/user/add")
    public ResponseEntity<Long> add(@RequestBody User user) {
        // Returns only the created id to save bandwidth.
        return ResponseEntity.ok(userRepository.save(user).getId());
    }

    // PUT will either create a new resource or update an existing one.
    @PutMapping(value = "/user/{id}/update")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody User user) {

        // Alternative to ifPresentOrElse.
        userRepository.findById((long) id)
            .map((existing) -> userRepository.save(user))
            .orElseThrow(() -> new NoSuchElementException(User.class.getSimpleName()));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/user/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable int id) {

        // delete already throws when entity is null, but maps to exception if optional is empty instead.
        userRepository.findById((long) id)
            .ifPresentOrElse(
                userRepository::delete,
                () -> { throw new NoSuchElementException(User.class.getSimpleName()); }
            );

        return ResponseEntity.ok().build();
    }
}
