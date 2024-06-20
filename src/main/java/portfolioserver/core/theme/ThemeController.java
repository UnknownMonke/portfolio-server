package portfolioserver.core.theme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolioserver.common.BaseController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ThemeController extends BaseController {
    
    private final ThemeRepository themeRepository;
    
    @Autowired
    public ThemeController(ThemeRepository themeRepository) {
        super(); // Enables access to logger.
        this.themeRepository = themeRepository;
    }

    @GetMapping(value = "/themes/get")
    public List<Theme> get() {
        return themeRepository.findAll();
    }

    // POST will create a child resource.
    @PostMapping(value = "/theme/add")
    public ResponseEntity<Long> add(@RequestBody String name) {

        // Returns only the created id to save bandwidth.
        return ResponseEntity.ok(themeRepository.save(new Theme(name)).getId());
    }

    /** The deleted theme is replaced with a null reference in users referencing this theme. */
    @DeleteMapping(value = "/theme/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable int id) {

        // delete already throws when entity is null, but maps to exception if optional is empty instead.
        themeRepository.findById((long) id)
            .ifPresentOrElse(
                themeRepository::delete,
                () -> { throw new NoSuchElementException(Theme.class.getSimpleName()); }
            );

        return ResponseEntity.ok().build();
    }
}
