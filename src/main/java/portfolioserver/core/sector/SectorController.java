package portfolioserver.core.sector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolioserver.common.BaseController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class SectorController extends BaseController {
    
    private final SectorRepository sectorRepository;

    @Autowired
    public SectorController(SectorRepository sectorRepository) {
        super(); // Enables access to logger.
        this.sectorRepository = sectorRepository;
    }

    @GetMapping(value = "/sectors/get")
    public List<Sector> get() {
        return sectorRepository.findAll();
    }

    @GetMapping(value = "/sector/{id}/get")
    public ResponseEntity<Sector> getOne(@PathVariable int id) {
        return ResponseEntity.ok(
            sectorRepository.findById((long) id)
                .orElseThrow(() -> new NoSuchElementException(Sector.class.getSimpleName()))
        );
    }

    @PostMapping(value = "/sector/add")
    public ResponseEntity<Long> add(@RequestBody Sector sector) {
        // Returns only the created id to save bandwidth.
        return ResponseEntity.ok(sectorRepository.save(sector).getId());
    }

    /** Each call overwrite the whole set of exposures. */
    @PostMapping(value = "/sector/{id}/exposures/add")
    public ResponseEntity<Void> addExposures(@PathVariable int id, @RequestBody List<SectorExposure> exposures) {

        sectorRepository.findById((long) id)
            .map((sector) -> {
                sector.setExposures(exposures);
                return sectorRepository.save(sector);
            })
            .orElseThrow(() -> new NoSuchElementException(Sector.class.getSimpleName()));

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/sector/{id}/update")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody String name) {

        // Alternative to ifPresentOrElse.
        sectorRepository.findById((long) id)
            .map((sector) -> {
                sector.setName(name);
                return sectorRepository.save(sector);
            })
            .orElseThrow(() -> new NoSuchElementException(Sector.class.getSimpleName()));

        return ResponseEntity.ok().build();
    }

    /** The deleted sector also deletes all associated exposures. */
    @DeleteMapping(value = "/sector/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable int id) {

        // delete all associated exposures entities as well.
        sectorRepository.findById((long) id)
            .ifPresentOrElse(
                sectorRepository::delete,
                () -> { throw new NoSuchElementException(Sector.class.getSimpleName()); }
            );

        return ResponseEntity.ok().build();
    }
}
