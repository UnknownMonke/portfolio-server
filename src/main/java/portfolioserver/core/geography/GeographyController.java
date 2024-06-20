package portfolioserver.core.geography;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolioserver.common.BaseController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class GeographyController extends BaseController {

    private final GeographyRepository geographyRepository;

    @Autowired
    public GeographyController(GeographyRepository geographyRepository) {
        super(); // Enables access to logger.
        this.geographyRepository = geographyRepository;
    }

    @GetMapping(value = "/geographies/get")
    public List<Geography> get() {
        return geographyRepository.findAll();
    }

    @GetMapping(value = "/geography/{id}/get")
    public ResponseEntity<Geography> getOne(@PathVariable int id) {
        return ResponseEntity.ok(
            geographyRepository.findById((long) id)
                .orElseThrow(() -> new NoSuchElementException(Geography.class.getSimpleName()))
        );
    }

    @PostMapping(value = "/geography/add")
    public ResponseEntity<Long> add(@RequestBody String name) {
        // Returns only the created id to save bandwidth.
        return ResponseEntity.ok(geographyRepository.save(new Geography(name)).getId());
    }

    /** Each call overwrite the whole set of exposures. */
    @PostMapping(value = "/geography/{id}/exposures/add")
    public ResponseEntity<Void> addExposures(@PathVariable int id, @RequestBody List<GeographyExposure> exposures) {

        geographyRepository.findById((long) id)
            .map((geography) -> {
                geography.setExposures(exposures);
                return geographyRepository.save(geography);
            })
            .orElseThrow(() -> new NoSuchElementException(Geography.class.getSimpleName()));

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/geography/{id}/update")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody String name) {

        // Alternative to ifPresentOrElse.
        geographyRepository.findById((long) id)
            .map((geography) -> {
                geography.setName(name);
                return geographyRepository.save(geography);
            })
            .orElseThrow(() -> new NoSuchElementException(Geography.class.getSimpleName()));

        return ResponseEntity.ok().build();
    }

    /** The deleted geography also deletes all associated exposures. */
    @DeleteMapping(value = "/geography/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable int id) {

        // delete all associated exposures entities as well.
        geographyRepository.findById((long) id)
            .ifPresentOrElse(
                geographyRepository::delete,
                () -> { throw new NoSuchElementException(Geography.class.getSimpleName()); }
            );

        return ResponseEntity.ok().build();
    }
}
